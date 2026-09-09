package com.example.ads

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.unity3d.mediation.LevelPlay
import com.unity3d.mediation.LevelPlayAdError
import com.unity3d.mediation.LevelPlayAdInfo
import com.unity3d.mediation.LevelPlayConfiguration
import com.unity3d.mediation.LevelPlayInitError
import com.unity3d.mediation.LevelPlayInitListener
import com.unity3d.mediation.LevelPlayInitRequest
import com.unity3d.mediation.rewarded.LevelPlayReward
import com.unity3d.mediation.rewarded.LevelPlayRewardedAd
import com.unity3d.mediation.rewarded.LevelPlayRewardedAdListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * LevelPlay (Unity Mediation) rewarded ads.
 *
 * App Key: 28098d405 (Android app in LevelPlay dashboard > Apps)
 * Rewarded Ad Unit: wljj57ixzqvzvcxo (LevelPlay dashboard > Setup > Ad Units)
 *
 * Docs flow: LevelPlay.init(appKey) -> onInitSuccess -> create LevelPlayRewardedAd(adUnitId)
 * -> setListener -> loadAd() -> onAdLoaded -> showAd(activity).
 * No Game ID / placement / GAID override like legacy Unity Ads.
 */
object LevelPlayAdsManager {
    private const val TAG = "LevelPlayAdsManager"

    const val APP_KEY = "28098d405"
    const val REWARDED_AD_UNIT_ID = "wljj57ixzqvzvcxo"

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

    private var appContext: Context? = null
    private var initializeCalled = false
    private var rewardedAd: LevelPlayRewardedAd? = null
    private val mainHandler = Handler(Looper.getMainLooper())

    // Pending callbacks for the current load/show cycle.
    private var pendingLoadSuccess: (() -> Unit)? = null
    private var pendingLoadError: ((String) -> Unit)? = null
    private var pendingShowRewarded: (() -> Unit)? = null
    private var pendingShowClosed: (() -> Unit)? = null
    private var pendingShowError: ((String) -> Unit)? = null
    private var rewardedThisShow = false

    private fun runOnMain(action: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) action()
        else mainHandler.post(action)
    }

    /**
     * Initialize LevelPlay. Call once from MainActivity.onCreate.
     * @param enableTestSuite pass true in debug to verify via LevelPlay test suite UI.
     */
    fun initialize(context: Context, enableTestSuite: Boolean = false) {
        appContext = context.applicationContext
        if (initializeCalled && _isInitialized.value) {
            if (!_isAdLoaded.value && !_isAdLoading.value) loadRewardedAd()
            return
        }
        initializeCalled = true

        if (enableTestSuite) {
            // Must be called BEFORE init per docs.
            LevelPlay.setMetaData("is_test_suite", "enable")
        }
        // Validates manifest / adapters / networks; logs rows to Logcat. Remove before live release.
        try {
            LevelPlay.validateIntegration(context.applicationContext)
        } catch (_: Exception) { /* validation is best-effort */ }
        Log.d(TAG, "Initializing LevelPlay: appKey=$APP_KEY adUnit=$REWARDED_AD_UNIT_ID")

        val initRequest = LevelPlayInitRequest.Builder(APP_KEY).build()
        LevelPlay.init(
            context.applicationContext,
            initRequest,
            object : LevelPlayInitListener {
                override fun onInitSuccess(configuration: LevelPlayConfiguration) {
                    Log.d(TAG, "LevelPlay init success: $configuration")
                    _isInitialized.value = true
                    _initializationError.value = null
                    runOnMain {
                        createRewardedAdIfNeeded()
                        loadRewardedAd()
                    }
                    if (enableTestSuite) {
                        runOnMain { launchTestSuite() }
                    }
                }

                override fun onInitFailed(error: LevelPlayInitError) {
                    Log.e(TAG, "LevelPlay init failed: $error")
                    _isInitialized.value = false
                    _initializationError.value =
                        "LevelPlay init failed ($error). Check App Key $APP_KEY, internet, " +
                            "and that the Android app exists in LevelPlay dashboard."
                }
            }
        )
    }

    fun launchTestSuite() {
        appContext?.let { LevelPlay.launchTestSuite(it) }
            ?: Log.w(TAG, "launchTestSuite: not initialized yet")
    }

    private fun createRewardedAdIfNeeded() {
        if (rewardedAd != null) return
        val ad = LevelPlayRewardedAd(REWARDED_AD_UNIT_ID)
        ad.setListener(object : LevelPlayRewardedAdListener {
            override fun onAdLoaded(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "LevelPlay rewarded loaded: $adInfo")
                _isAdLoaded.value = true
                _isAdLoading.value = false
                _lastLoadError.value = null
                pendingLoadSuccess?.invoke()
                clearLoadCallbacks()
            }

            override fun onAdLoadFailed(error: LevelPlayAdError) {
                Log.e(TAG, "LevelPlay rewarded load failed: $error")
                _isAdLoaded.value = false
                _isAdLoading.value = false
                val msg = friendlyLoadError(error)
                _lastLoadError.value = msg
                pendingLoadError?.invoke(msg)
                clearLoadCallbacks()
            }

            override fun onAdDisplayed(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "LevelPlay rewarded displayed: $adInfo")
            }

            override fun onAdDisplayFailed(error: LevelPlayAdError, adInfo: LevelPlayAdInfo) {
                Log.e(TAG, "LevelPlay rewarded display failed: $error info: $adInfo")
                _isAdLoaded.value = false
                pendingShowError?.invoke("Could not show ad ($error). Reloaded - please Retry.")
                clearShowCallbacks()
                loadRewardedAd()
            }

            override fun onAdClicked(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "LevelPlay rewarded clicked: $adInfo")
            }

            override fun onAdClosed(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "LevelPlay rewarded closed, rewarded=$rewardedThisShow")
                _isAdLoaded.value = false
                if (rewardedThisShow) pendingShowRewarded?.invoke()
                else pendingShowClosed?.invoke()
                clearShowCallbacks()
                loadRewardedAd()
            }

            override fun onAdRewarded(reward: LevelPlayReward, adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "LevelPlay rewarded: ${reward.name} x${reward.amount}")
                rewardedThisShow = true
            }

            override fun onAdInfoChanged(adInfo: LevelPlayAdInfo) {
                Log.d(TAG, "LevelPlay ad info changed: $adInfo")
            }
        })
        rewardedAd = ad
    }

    fun loadRewardedAd(
        onLoaded: (() -> Unit)? = null,
        onFailed: ((String) -> Unit)? = null
    ) {
        if (!_isInitialized.value) {
            appContext?.let { initialize(it) }
            val msg = "LevelPlay is still initializing. Please wait and retry."
            _lastLoadError.value = msg
            onFailed?.invoke(msg)
            return
        }
        if (_isAdLoading.value) {
            // A preload is already in flight: chain the new callbacks onto it
            // instead of dropping them, or the UI would wait forever.
            Log.d(TAG, "Load already in progress, chaining callbacks")
            if (onLoaded != null) {
                val prev = pendingLoadSuccess
                pendingLoadSuccess = { prev?.invoke(); onLoaded() }
            }
            if (onFailed != null) {
                val prev = pendingLoadError
                pendingLoadError = { prev?.invoke(it); onFailed(it) }
            }
            return
        }
        runOnMain {
            createRewardedAdIfNeeded()
            _isAdLoading.value = true
            pendingLoadSuccess = onLoaded
            pendingLoadError = onFailed
            rewardedAd?.loadAd()
        }
    }

    fun loadAndShowRewardedAd(
        activity: Activity,
        onRewarded: () -> Unit,
        onClosed: () -> Unit,
        onError: (String) -> Unit
    ) {
        val ad = rewardedAd
        if (_isInitialized.value && ad != null && _isAdLoaded.value && ad.isAdReady()) {
            showRewardedAd(activity, onRewarded, onClosed, onError)
        } else {
            loadRewardedAd(
                onLoaded = { showRewardedAd(activity, onRewarded, onClosed, onError) },
                onFailed = { onError(it) }
            )
        }
    }

    fun showRewardedAd(
        activity: Activity,
        onRewarded: () -> Unit,
        onClosed: () -> Unit,
        onError: (String) -> Unit
    ) {
        val ad = rewardedAd
        if (!_isInitialized.value || ad == null) {
            onError("LevelPlay is still initializing. Please wait and retry.")
            return
        }
        if (!ad.isAdReady()) {
            loadAndShowRewardedAd(activity, onRewarded, onClosed, onError)
            return
        }
        runOnMain {
            rewardedThisShow = false
            pendingShowRewarded = onRewarded
            pendingShowClosed = onClosed
            pendingShowError = onError
            ad.showAd(activity)
        }
    }

    private fun clearLoadCallbacks() {
        pendingLoadSuccess = null
        pendingLoadError = null
    }

    private fun clearShowCallbacks() {
        pendingShowRewarded = null
        pendingShowClosed = null
        pendingShowError = null
        rewardedThisShow = false
    }

    private fun friendlyLoadError(error: LevelPlayAdError): String {
        return "Ad load failed ($error). Checklist: 1) App Key $APP_KEY + ad unit " +
            "$REWARDED_AD_UNIT_ID exist in LevelPlay dashboard, 2) Rewarded ad unit has " +
            "a live network (Unity Ads bidder) with test mode on, 3) emulator has internet. " +
            "Use Test Suite to verify each network."
    }
}
