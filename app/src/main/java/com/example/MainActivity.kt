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
import com.example.ads.LevelPlayAdsManager
import com.example.data.AppMode
import com.example.ui.components.AdPromptDialog
import com.example.ui.components.AdPurpose
import com.example.ui.components.LockedLevelDialog
import com.example.ui.components.RewardedAdPlayerModal
import com.example.ui.components.LevelPlayAdErrorModal
import com.example.ui.components.LevelPlayAdLoadingModal
import com.example.ui.screens.LevelGridScreen
import com.example.ui.screens.ModeSelectionScreen
import com.example.ui.screens.PackSelectionScreen
import com.example.ui.screens.QuizPlayScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.QuizViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize LevelPlay mediation with App Key: 28098d405.
        // No testMode flag: app stays in Test Mode until disabled in LevelPlay dashboard.
        LevelPlayAdsManager.initialize(this)

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
    val isAdLoaded by LevelPlayAdsManager.isAdLoaded.collectAsState()
    var isAdLoading by remember { mutableStateOf(false) }
    var adError by remember { mutableStateOf<String?>(null) }
    var showFallbackModal by remember { mutableStateOf(false) }

    val userProfile by viewModel.userProfile.collectAsState()
    val packSummaries by viewModel.packSummaries.collectAsState()
    val allProgress by viewModel.allProgress.collectAsState()
    val currentPackId by viewModel.currentPackId.collectAsState()
    val currentLevelId by viewModel.currentLevelId.collectAsState()
    val activeLevel by viewModel.activeLevel.collectAsState()
    val appMode by viewModel.appMode.collectAsState()

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
    val lockedLevelNotice by viewModel.lockedLevelNotice.collectAsState()
    val isRefreshingLevels by viewModel.isRefreshingLevels.collectAsState()
    val refreshStatusMessage by viewModel.refreshStatusMessage.collectAsState()

    // Navigation back handlers
    if (currentLevelId != null) {
        BackHandler {
            viewModel.closeLevelToGrid()
        }
    } else if (currentPackId != null) {
        BackHandler {
            viewModel.selectPack(null)
        }
    } else if (appMode != null) {
        BackHandler {
            viewModel.setAppMode(null)
        }
    }

    val screenState = when {
        appMode == null -> "MODE_SELECTION"
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
            "MODE_SELECTION" -> {
                ModeSelectionScreen(
                    onSelectMode = viewModel::setAppMode
                )
            }

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
                        isAdminMode = (appMode == AppMode.ADMIN),
                        isRefreshing = isRefreshingLevels,
                        refreshMessage = refreshStatusMessage,
                        onRefreshClick = { viewModel.refreshRemoteLogos(packId) },
                        onClearRefreshMessage = viewModel::clearRefreshMessage,
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
                    isAdminMode = (appMode == AppMode.ADMIN),
                    onSelectPack = viewModel::selectPack,
                    onQuickPlayClick = viewModel::startQuickPlay,
                    onEarnCoinsClick = viewModel::promptEarnCoinsAd,
                    onSwitchMode = { viewModel.setAppMode(null) },
                    isRefreshing = isRefreshingLevels,
                    onRefreshClick = { viewModel.refreshRemoteLogos() }
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

    // Locked Level Sequential Requirement Dialog
    lockedLevelNotice?.let { notice ->
        LockedLevelDialog(
            targetLevel = notice.targetLevel,
            requiredLevel = notice.requiredLevel,
            onPlayRequiredLevel = {
                viewModel.playRequiredLevelFromNotice()
            },
            onDismiss = viewModel::dismissLockedLevelNotice
        )
    }

    // Reset transient ad states when showAdPlayer turns false
    LaunchedEffect(showAdPlayer) {
        if (!showAdPlayer) {
            isAdLoading = false
            adError = null
            showFallbackModal = false
        }
    }

    // Global Rewarded Video Ad Player (LevelPlay with responsive loading & error resilience)
    LaunchedEffect(showAdPlayer) {
        if (showAdPlayer && activity != null) {
            adError = null
            showFallbackModal = false
            if (isAdLoaded) {
                // Ad is ready in memory: show immediately!
                isAdLoading = false
                LevelPlayAdsManager.showRewardedAd(
                    activity = activity,
                    onRewarded = {
                        viewModel.onAdPlayerFinished(pendingAdPurpose)
                    },
                    onClosed = {
                        viewModel.onCloseAdPlayer()
                    },
                    onError = { err ->
                        adError = err
                    }
                )
            } else {
                // Ad not preloaded yet: trigger load & show with active loading indicator
                isAdLoading = true
                LevelPlayAdsManager.loadAndShowRewardedAd(
                    activity = activity,
                    onRewarded = {
                        isAdLoading = false
                        viewModel.onAdPlayerFinished(pendingAdPurpose)
                    },
                    onClosed = {
                        isAdLoading = false
                        viewModel.onCloseAdPlayer()
                    },
                    onError = { err ->
                        isAdLoading = false
                        adError = err
                    }
                )
            }
        }
    }

    // Ad Loading Screen while retrieving ad from LevelPlay mediation
    if (showAdPlayer && isAdLoading) {
        LevelPlayAdLoadingModal(
            onDismiss = {
                isAdLoading = false
                viewModel.onCloseAdPlayer()
            }
        )
    }

    // Notice dialog if LevelPlay returns an issue
    if (showAdPlayer && adError != null) {
        LevelPlayAdErrorModal(
            errorMessage = adError ?: "Ad request could not be completed.",
            onRetry = {
                adError = null
                if (activity != null) {
                    isAdLoading = true
                    LevelPlayAdsManager.loadAndShowRewardedAd(
                        activity = activity,
                        onRewarded = {
                            isAdLoading = false
                            viewModel.onAdPlayerFinished(pendingAdPurpose)
                        },
                        onClosed = {
                            isAdLoading = false
                            viewModel.onCloseAdPlayer()
                        },
                        onError = { err ->
                            isAdLoading = false
                            adError = err
                        }
                    )
                }
            },
            onFallback = {
                adError = null
                showFallbackModal = true
            },
            onDismiss = {
                adError = null
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
