package com.example.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.unity3d.ads.IUnityAdsInitializationListener
import com.unity3d.ads.IUnityAdsLoadListener
import com.unity3d.ads.IUnityAdsShowListener
import com.unity3d.ads.UnityAds
import com.unity3d.ads.UnityAdsLoadOptions
import com.unity3d.ads.UnityAdsShowOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Unity Ads integration helper for rewarded video placements.
 * Configured with Game ID: 800370319 and Placement: Rewarded_Android.
 */
object UnityAdsManager {
    private const val TAG = "UnityAdsManager"

    // Unity Ads Game ID & Placement provided by user
    const val GAME_ID = "800370319"
    const val REWARDED_PLACEMENT_ID = "Rewarded_Android"

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

    private var appContext: Context? = null

    /**
     * Initialize the Unity Ads SDK.
     */
    fun initialize(context: Context, testMode: Boolean = true) {
        this.appContext = context.applicationContext
        this.testMode = testMode
        if (UnityAds.isInitialized) {
            _isInitialized.value = true
            loadRewardedAd()
            return
        }

        UnityAds.initialize(
            context.applicationContext,
            GAME_ID,
            testMode,
            object : IUnityAdsInitializationListener {
                override fun onInitializationComplete() {
                    Log.d(TAG, "Unity Ads initialized successfully for Game ID: $GAME_ID")
                    _isInitialized.value = true
                    loadRewardedAd()
                }

                override fun onInitializationFailed(
                    error: UnityAds.UnityAdsInitializationError?,
                    message: String?
                ) {
                    Log.e(TAG, "Unity Ads initialization failed: $error, message: $message")
                    _isInitialized.value = false
                }
            }
        )
    }

    /**
     * Preload the rewarded ad.
     */
    fun loadRewardedAd(
        onLoaded: (() -> Unit)? = null,
        onFailed: ((String) -> Unit)? = null
    ) {
        if (!UnityAds.isInitialized) {
            Log.w(TAG, "Unity Ads not initialized yet")
            appContext?.let { initialize(it, testMode) }
            onFailed?.invoke("Unity Ads not initialized yet")
            return
        }

        _isAdLoading.value = true
        UnityAds.load(
            REWARDED_PLACEMENT_ID,
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
                    Log.e(TAG, "Unity Ads failed to load for placement $placementId: $error, message: $message")
                    _isAdLoaded.value = false
                    _isAdLoading.value = false
                    val humanMsg = if (message?.contains("adMarkup", ignoreCase = true) == true ||
                        message?.contains("Header bidding", ignoreCase = true) == true
                    ) {
                        "Unity Ads placement requires Test Mode override in Unity Dashboard: Navigate to Project > Settings > Test mode and enable 'Override client test mode' (Force test mode ON) for your device."
                    } else {
                        message ?: "Failed to load ad"
                    }
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
        if (_isAdLoaded.value) {
            showRewardedAd(activity, onRewarded, onClosed, onError)
        } else {
            _isAdLoading.value = true
            loadRewardedAd(
                onLoaded = {
                    _isAdLoading.value = false
                    showRewardedAd(activity, onRewarded, onClosed, onError)
                },
                onFailed = { errorMessage ->
                    _isAdLoading.value = false
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
                    onError(message ?: "Failed to show ad")
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
}
