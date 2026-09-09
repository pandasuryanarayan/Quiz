package com.example

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ads.UnityAdsManager
import com.example.ui.components.AdPromptDialog
import com.example.ui.components.AdPurpose
import com.example.ui.components.RewardedAdPlayerModal
import com.example.ui.components.UnityAdErrorModal
import com.example.ui.components.UnityAdLoadingModal
import com.example.ui.screens.LevelGridScreen
import com.example.ui.screens.PackSelectionScreen
import com.example.ui.screens.QuizPlayScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Unity Ads SDK with Game ID: 800370319
        UnityAdsManager.initialize(this, testMode = true)

        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LogoQuizApp()
                }
            }
        }
    }
}

@Composable
fun LogoQuizApp(
    viewModel: QuizViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val isUnityAdLoaded by UnityAdsManager.isAdLoaded.collectAsState()
    var isUnityAdLoading by remember { mutableStateOf(false) }
    var unityError by remember { mutableStateOf<String?>(null) }
    var showFallbackModal by remember { mutableStateOf(false) }

    val userProfile by viewModel.userProfile.collectAsState()
    val packSummaries by viewModel.packSummaries.collectAsState()
    val allProgress by viewModel.allProgress.collectAsState()
    val currentPackId by viewModel.currentPackId.collectAsState()
    val currentLevelId by viewModel.currentLevelId.collectAsState()
    val activeLevel by viewModel.activeLevel.collectAsState()

    val slots by viewModel.slots.collectAsState()
    val bankTiles by viewModel.bankTiles.collectAsState()
    val slotState by viewModel.slotState.collectAsState()
    val shakeTrigger by viewModel.shakeTrigger.collectAsState()
    val freeHintUsed by viewModel.freeHintUsedOnLevel.collectAsState()

    val showHintDialog by viewModel.showHintDialog.collectAsState()
    val showAdPrompt by viewModel.showAdPrompt.collectAsState()
    val showAdPlayer by viewModel.showAdPlayer.collectAsState()
    val showLevelComplete by viewModel.showLevelComplete.collectAsState()
    val pendingAdPurpose by viewModel.pendingAdPurpose.collectAsState()
    val pendingUnlockLevel by viewModel.pendingUnlockLevel.collectAsState()

    // Navigation back handlers
    if (currentLevelId != null) {
        BackHandler {
            viewModel.closeLevelToGrid()
        }
    } else if (currentPackId != null) {
        BackHandler {
            viewModel.selectPack(null)
        }
    }

    val screenState = when {
        currentLevelId != null && activeLevel != null -> "PLAY"
        currentPackId != null -> "GRID"
        else -> "PACKS"
    }

    AnimatedContent(
        targetState = screenState,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "ScreenTransition"
    ) { state ->
        when (state) {
            "PLAY" -> {
                activeLevel?.let { level ->
                    QuizPlayScreen(
                        level = level,
                        slots = slots,
                        bankTiles = bankTiles,
                        slotState = slotState,
                        shakeTrigger = shakeTrigger,
                        coins = userProfile?.coins ?: 150,
                        freeHintAvailable = !freeHintUsed,
                        showHintDialog = showHintDialog,
                        showLevelComplete = showLevelComplete,
                        onBankTileClick = viewModel::onBankTileClick,
                        onSlotClick = viewModel::onSlotClick,
                        onShuffle = viewModel::onShuffleBank,
                        onClearAll = viewModel::onClearAll,
                        onOpenHint = viewModel::openHintMenu,
                        onCloseHint = viewModel::closeHintMenu,
                        onUseFreeHint = viewModel::useFreeHint,
                        onAdHintRemoveLetters = viewModel::promptAdHintRemoveLetters,
                        onAdHintRevealLetter = viewModel::promptAdHintRevealLetter,
                        onUseCoinHint = viewModel::useCoinHint,
                        onEarnCoinsClick = viewModel::promptEarnCoinsAd,
                        onNextLevel = viewModel::goToNextLevel,
                        onDoubleCoinsAd = viewModel::onDoubleCoinsAd,
                        onBack = viewModel::closeLevelToGrid
                    )
                }
            }

            "GRID" -> {
                currentPackId?.let { packId ->
                    LevelGridScreen(
                        packId = packId,
                        allProgress = allProgress,
                        coins = userProfile?.coins ?: 150,
                        onLevelClick = viewModel::openLevel,
                        onBack = { viewModel.selectPack(null) },
                        onEarnCoinsClick = viewModel::promptEarnCoinsAd
                    )
                }
            }

            else -> {
                PackSelectionScreen(
                    packSummaries = packSummaries,
                    userProfile = userProfile,
                    onSelectPack = viewModel::selectPack,
                    onEarnCoinsClick = viewModel::promptEarnCoinsAd
                )
            }
        }
    }

    // Global Ad Unlock Prompt Dialog (Level unlock requests from Grid or Play)
    if (showAdPrompt) {
        AdPromptDialog(
            purpose = pendingAdPurpose,
            levelNumber = pendingUnlockLevel?.levelNumber ?: activeLevel?.levelNumber ?: 0,
            onConfirmWatch = viewModel::onConfirmWatchAd,
            onDismiss = viewModel::onDismissAdPrompt
        )
    }

    // Reset transient ad states when showAdPlayer turns false
    LaunchedEffect(showAdPlayer) {
        if (!showAdPlayer) {
            isUnityAdLoading = false
            unityError = null
            showFallbackModal = false
        }
    }

    // Global Rewarded Video Ad Player (Direct Unity Ads with responsive loading & error resilience)
    LaunchedEffect(showAdPlayer) {
        if (showAdPlayer && activity != null) {
            unityError = null
            showFallbackModal = false
            if (isUnityAdLoaded) {
                // Ad is ready in memory: show immediately!
                isUnityAdLoading = false
                UnityAdsManager.showRewardedAd(
                    activity = activity,
                    onRewarded = {
                        viewModel.onAdPlayerFinished(pendingAdPurpose)
                    },
                    onClosed = {
                        viewModel.onCloseAdPlayer()
                    },
                    onError = { err ->
                        unityError = err
                    }
                )
            } else {
                // Ad not preloaded yet: trigger load & show with active loading indicator
                isUnityAdLoading = true
                UnityAdsManager.loadAndShowRewardedAd(
                    activity = activity,
                    onRewarded = {
                        isUnityAdLoading = false
                        viewModel.onAdPlayerFinished(pendingAdPurpose)
                    },
                    onClosed = {
                        isUnityAdLoading = false
                        viewModel.onCloseAdPlayer()
                    },
                    onError = { err ->
                        isUnityAdLoading = false
                        unityError = err
                    }
                )
            }
        }
    }

    // Unity Ad Loading Screen while retrieving ad from Unity Servers for this test device
    if (showAdPlayer && isUnityAdLoading) {
        UnityAdLoadingModal(
            onDismiss = {
                isUnityAdLoading = false
                viewModel.onCloseAdPlayer()
            }
        )
    }

    // Notice dialog if Unity Servers return an issue
    if (showAdPlayer && unityError != null) {
        UnityAdErrorModal(
            errorMessage = unityError ?: "Ad request could not be completed.",
            onRetry = {
                unityError = null
                if (activity != null) {
                    isUnityAdLoading = true
                    UnityAdsManager.loadAndShowRewardedAd(
                        activity = activity,
                        onRewarded = {
                            isUnityAdLoading = false
                            viewModel.onAdPlayerFinished(pendingAdPurpose)
                        },
                        onClosed = {
                            isUnityAdLoading = false
                            viewModel.onCloseAdPlayer()
                        },
                        onError = { err ->
                            isUnityAdLoading = false
                            unityError = err
                        }
                    )
                }
            },
            onFallback = {
                unityError = null
                showFallbackModal = true
            },
            onDismiss = {
                unityError = null
                viewModel.onCloseAdPlayer()
            }
        )
    }

    // Fallback simulation player if explicitly requested
    if (showAdPlayer && showFallbackModal) {
        RewardedAdPlayerModal(
            purpose = pendingAdPurpose,
            onAdCompleted = {
                showFallbackModal = false
                viewModel.onAdPlayerFinished(pendingAdPurpose)
            },
            onClose = {
                showFallbackModal = false
                viewModel.onCloseAdPlayer()
            }
        )
    }
}

