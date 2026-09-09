package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAdsLoadOptions
import com.unity3d.ads.UnityAdsShowOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Unity Ads integration helper for rewarded video placements.
 *
 * Game ID: 800370319 (must be the ANDROID Game ID from Unity Dashboard)
 * Placement: Rewarded_Android (must exist + be Active in Dashboard > Monetization > Placements)
 *
 * Most common reasons ads don't load on an emulator:
 *  1. Wrong Game ID (used iOS ID on Android, or typo).
 *  2. Placement "Rewarded_Android" doesn't exist / not active yet (new placements take ~1-2h).
 *  3. Test device GAID not registered in Dashboard + "Override client test mode" OFF.
 *  4. Emulator image without Google Play / no internet / different GAID than registered.
 *  5. Store (package com.aistudio.logoquiz.wqmz) not linked in Unity project.
 */
object UnityAdsManager {
    private const val TAG = "UnityAdsManager"

    // Unity Ads Game ID & Placement provided by user
    const val GAME_ID = "800370319"
    const val REWARDED_PLACEMENT_ID = "Rewarded_Android"

    // GAID the user registered for the emulator. Used only for log comparison.
    // The emulator's REAL current GAID is fetched at runtime (see deviceAdvertisingId).
    const val REGISTERED_TEST_GAID = "bb344dae-28be-4b36-9c40-ee746815dc18"

    // Set testMode to true for sandbox/development testing, false for live ad production
    var testMode: Boolean = true

    private val _isInitialized = MutableStateFlow(false)
    val isInitialized: StateFlow<Boolean> = _isInitialized.asStateFlow()

    private val _isAdLoaded = MutableStateFlow(false)
    val isAdLoaded: StateFlow<Boolean> = _isAdLoaded.asStateFlow()

    private val _isAdLoading = MutableStateFlow(false)
    val isAdLoading: StateFlow<Boolean> = _isAdLoading.asStateFlow()

    private val _lastLoadError = MutableStateFlow<String?>(null)
    val lastLoadError: StateFlow<String?> = _lastLoadError.asStateFlow()

    private val _initializationError = MutableStateFlow<String?>(null)
    val initializationError: StateFlow<String?> = _initializationError.asStateFlow()

    private val _deviceAdvertisingId = MutableStateFlow<String?>(null)

    /** The emulator/device's actual current GAID. Compare with REGISTERED_TEST_GAID in Logcat. */
    val deviceAdvertisingId: StateFlow<String?> = _deviceAdvertisingId.asStateFlow()

    private var appContext: Context? = null
    private var initializeCalled = false
    private val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Initialize the Unity Ads SDK. Safe to call once from MainActivity.onCreate.
     */
    fun initialize(context: Context, testMode: Boolean = true) {
        this.appContext = context.applicationContext
        this.testMode = testMode

        // Always fetch + log the real GAID so dashboard mismatch is obvious.
        fetchAndLogAdvertisingId(context.applicationContext)

        if (initializeCalled && UnityAds.isInitialized()) {
            _isInitialized.value = true
            if (!_isAdLoaded.value && !_isAdLoading.value) loadRewardedAd()
            return
        }
        initializeCalled = true

        // Verbose Unity SDK logs make dashboard/GameID/placement errors visible in Logcat.
        UnityAds.setDebugMode(testMode)
        Log.d(TAG, "Initializing Unity Ads: gameId=$GAME_ID testMode=$testMode " +
                "placement=$REWARDED_PLACEMENT_ID registeredGaid=$REGISTERED_TEST_GAID")

        UnityAds.initialize(
            context.applicationContext,
            GAME_ID,
            testMode,
            object : IUnityAdsInitializationListener {
                override fun onInitializationComplete() {
                    Log.d(TAG, "Unity Ads initialized successfully for Game ID: $GAME_ID")
                    _isInitialized.value = true
                    _initializationError.value = null
                    loadRewardedAd()
                }

                override fun onInitializationFailed(
                    error: UnityAds.UnityAdsInitializationError?,
                    message: String?
                ) {
                    Log.e(TAG, "Unity Ads initialization failed: $error, message: $message")
                    _isInitialized.value = false
                    _initializationError.value = friendlyInitError(error, message)
                }
            }
        )
    }

    /**
     * Fetch the real Google Advertising ID on a background thread.
     * Unity Dashboard test-device matching uses THIS value, not Settings.Secure.ANDROID_ID.
     * Check Logcat tag UnityAdsManager to copy it into Dashboard > Test devices.
     */
    fun fetchAndLogAdvertisingId(context: Context) {
        ioScope.launch {
            try {
                val info = AdvertisingIdClient.getAdvertisingIdInfo(context)
                val gaid = info?.id
                _deviceAdvertisingId.value = gaid
                Log.d(TAG, "Device GAID (register this in Unity Dashboard > Test devices): $gaid " +
                        "limitAdTracking=${info?.isLimitAdTrackingEnabled}")
                if (!gaid.isNullOrBlank() && !gaid.equals(REGISTERED_TEST_GAID, ignoreCase = true)) {
                    Log.w(TAG, "GAID MISMATCH! Emulator reports $gaid but you registered " +
                            "$REGISTERED_TEST_GAID. Update Dashboard or re-check emulator " +
                            "(Settings > Google > Ads > Your advertising ID). " +
                            "Wipe emulator data changes the GAID.")
                }
            } catch (e: Exception) {
                Log.w(TAG, "Could not read GAID (Play Services missing on emulator?): ${e.message}. " +
                        "Use a 'Google Play' emulator image, not 'Google APIs'.", e)
            }
        }
    }

    /**
     * Preload the rewarded ad.
     */
    fun loadRewardedAd(
        onLoaded: (() -> Unit)? = null,
        onFailed: ((String) -> Unit)? = null
    ) {
        if (!UnityAds.isInitialized()) {
            Log.w(TAG, "Unity Ads not initialized yet - initializing now")
            appContext?.let { initialize(it, testMode) }
            val msg = "Unity Ads is still initializing. Please wait a few seconds and retry."
            _lastLoadError.value = msg
            onFailed?.invoke(msg)
            return
        }

        if (_isAdLoading.value) {
            Log.d(TAG, "Load already in progress, skipping duplicate load")
            return
        }

        _isAdLoading.value = true
        // Explicit options object is the recommended 4.x usage.
        val loadOptions = UnityAdsLoadOptions()
        UnityAds.load(
            REWARDED_PLACEMENT_ID,
            loadOptions,
            object : IUnityAdsLoadListener {
                override fun onUnityAdsAdLoaded(placementId: String?) {
                    Log.d(TAG, "Unity Ads rewarded ad loaded for placement: $placementId")
                    _isAdLoaded.value = true
                    _isAdLoading.value = false
                    _lastLoadError.value = null
                    onLoaded?.invoke()
                }

                override fun onUnityAdsFailedToLoad(
                    placementId: String?,
                    error: UnityAds.UnityAdsLoadError?,
                    message: String?
                ) {
                    Log.e(TAG, "Unity Ads failed to load for placement $placementId: $error, message: $message " +
                            "gaid=${_deviceAdvertisingId.value}")
                    _isAdLoaded.value = false
                    _isAdLoading.value = false
                    val humanMsg = friendlyLoadError(error, message)
                    _lastLoadError.value = humanMsg
                    onFailed?.invoke(humanMsg)
                }
            }
        )
    }

    /**
     * Load and show the rewarded ad in one call. If already loaded, shows immediately.
     */
    fun loadAndShowRewardedAd(
        activity: Activity,
        onRewarded: () -> Unit,
        onClosed: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (_isAdLoaded.value && UnityAds.isInitialized()) {
            showRewardedAd(activity, onRewarded, onClosed, onError)
        } else {
            loadRewardedAd(
                onLoaded = {
                    showRewardedAd(activity, onRewarded, onClosed, onError)
                },
                onFailed = { errorMessage ->
                    onError(errorMessage)
                }
            )
        }
    }

    /**
     * Show the rewarded ad. If successful and user completes the ad, onRewarded is called.
     */
    fun showRewardedAd(
        activity: Activity,
        onRewarded: () -> Unit,
        onClosed: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!UnityAds.isInitialized()) {
            onError("Unity Ads is still initializing. Please wait and retry.")
            return
        }
        val showOptions = UnityAdsShowOptions()
        UnityAds.show(
            activity,
            REWARDED_PLACEMENT_ID,
            showOptions,
            object : IUnityAdsShowListener {
                override fun onUnityAdsShowFailure(
                    placementId: String?,
                    error: UnityAds.UnityAdsShowError?,
                    message: String?
                ) {
                    Log.e(TAG, "Unity Ads show failure: $error, message: $message")
                    _isAdLoaded.value = false
                    loadRewardedAd()
                    onError(friendlyShowError(error, message))
                }

                override fun onUnityAdsShowStart(placementId: String?) {
                    Log.d(TAG, "Unity Ads show started: $placementId")
                }

                override fun onUnityAdsShowClick(placementId: String?) {
                    Log.d(TAG, "Unity Ads show clicked: $placementId")
                }

                override fun onUnityAdsShowComplete(
                    placementId: String?,
                    state: UnityAds.UnityAdsShowCompletionState?
                ) {
                    Log.d(TAG, "Unity Ads show complete: $placementId, state: $state")
                    _isAdLoaded.value = false
                    // Pre-reload next ad
                    loadRewardedAd()

                    if (state == UnityAds.UnityAdsShowCompletionState.COMPLETED) {
                        onRewarded()
                    } else {
                        onClosed()
                    }
                }
            }
        )
    }

    private fun friendlyInitError(
        error: UnityAds.UnityAdsInitializationError?,
        message: String?
    ): String {
        val raw = listOfNotNull(error?.toString(), message).joinToString(": ").ifBlank { "unknown error" }
        return "Unity Ads init failed ($raw). Check: 1) Game ID $GAME_ID is the ANDROID ID " +
                "(Dashboard > Games > Android, not iOS), 2) internet on emulator, " +
                "3) package com.aistudio.logoquiz.wqmz linked to this Unity project. See Logcat tag UnityAdsManager."
    }

    private fun friendlyLoadError(
        error: UnityAds.UnityAdsLoadError?,
        message: String?
    ): String {
        val raw = listOfNotNull(error?.toString(), message).joinToString(": ")
        // Header-bidding/adMarkup error = test-mode override missing (very common on SDK 4.x).
        if (message?.contains("adMarkup", ignoreCase = true) == true ||
            message?.contains("Header bidding", ignoreCase = true) == true
        ) {
            return "Test ads blocked: Dashboard > Project ($GAME_ID) > Settings > Test mode > " +
                    "enable 'Override client test mode' (Force test mode ON) and add test device " +
                    "GAID ${_deviceAdvertisingId.value ?: REGISTERED_TEST_GAID}. Then reload. ($raw)"
        }
        return when (error) {
            UnityAds.UnityAdsLoadError.INVALID_ARGUMENT ->
                "Invalid placement/Game ID ($raw). Verify placement '$REWARDED_PLACEMENT_ID' exists, " +
                        "is Active + type Rewarded, and Game ID $GAME_ID is the Android ID."
            UnityAds.UnityAdsLoadError.NO_FILL ->
                "No test ad fill ($raw). Fix checklist: 1) Dashboard test device GAID = " +
                        "${_deviceAdvertisingId.value ?: REGISTERED_TEST_GAID} (see Logcat), " +
                        "2) 'Override client test mode' ON, 3) placement '$REWARDED_PLACEMENT_ID' Active " +
                        "(new placements need ~1-2h), 4) Google Play emulator image + internet."
            UnityAds.UnityAdsLoadError.TIMEOUT ->
                "Ad request timed out ($raw). Check emulator internet (open Chrome), then Retry."
            UnityAds.UnityAdsLoadError.INITIALIZE_FAILED ->
                "SDK not initialized ($raw). Wait for init complete (Logcat: 'initialized successfully'), then Retry."
            else ->
                if (raw.isBlank()) "Failed to load ad. Retry, or check Logcat tag UnityAdsManager."
                else "Ad load failed: $raw. Check Logcat tag UnityAdsManager for GAID/placement details."
        }
    }

    private fun friendlyShowError(
        error: UnityAds.UnityAdsShowError?,
        message: String?
    ): String {
        val raw = listOfNotNull(error?.toString(), message).joinToString(": ").ifBlank { "unknown error" }
        return "Could not show ad ($raw). Ad reloaded - please Retry. If NOT_INITIALIZED, wait a few seconds first."
    }
}
