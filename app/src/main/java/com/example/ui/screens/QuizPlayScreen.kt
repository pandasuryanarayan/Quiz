package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.QuizLevel
import com.example.ui.components.BankTile
import com.example.ui.components.HintDialog
import com.example.ui.components.LetterBank
import com.example.ui.components.LetterSlotsRow
import com.example.ui.components.LevelCompleteDialog
import com.example.ui.components.LogoCard
import com.example.ui.components.SlotItem
import com.example.ui.components.SlotState
import com.example.ui.theme.ArcadeBg
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeBorderBright
import com.example.ui.theme.ArcadeCanvas
import com.example.ui.theme.ArcadeCard
import com.example.ui.theme.ArcadeCardElevated
import com.example.ui.theme.ArcadeFlame
import com.example.ui.theme.ArcadeGold
import com.example.ui.theme.ArcadeNeonCyan
import com.example.ui.theme.ArcadeNeonGreen
import com.example.ui.theme.ArcadeText
import com.example.ui.theme.ArcadeTextDim
import com.example.ui.theme.ArcadeTextMuted

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizPlayScreen(
    level: QuizLevel,
    slots: List<SlotItem>,
    bankTiles: List<BankTile>,
    slotState: SlotState,
    shakeTrigger: Int,
    coins: Int,
    freeHintAvailable: Boolean,
    showHintDialog: Boolean,
    showLevelComplete: Boolean,
    onBankTileClick: (Int) -> Unit,
    onSlotClick: (Int) -> Unit,
    onShuffle: () -> Unit,
    onClearAll: () -> Unit,
    onOpenHint: () -> Unit,
    onCloseHint: () -> Unit,
    onUseFreeHint: () -> Unit,
    onAdHintRemoveLetters: () -> Unit,
    onAdHintRevealLetter: () -> Unit,
    onUseCoinHint: () -> Unit,
    onEarnCoinsClick: () -> Unit,
    onNextLevel: () -> Unit,
    onDoubleCoinsAd: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progressFraction = (level.levelNumber.toFloat() / 10f).coerceIn(0f, 1f)

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Surface(
                        shape = CircleShape,
                        color = ArcadeCardElevated,
                        border = BorderStroke(1.dp, ArcadeBorder),
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(38.dp)
                            .clickable { onBack() }
                            .testTag("back_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close",
                                tint = ArcadeText,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                title = {
                    // Level progress pill: level bubble + progress bar
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = ArcadeCardElevated,
                        border = BorderStroke(1.dp, ArcadeBorder),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = ArcadeFlame,
                                modifier = Modifier.size(20.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${level.levelNumber}",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 11.sp,
                                        color = ArcadeCanvas
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            LinearProgressIndicator(
                                progress = { progressFraction },
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = ArcadeFlame,
                                trackColor = Color(0xFF0F0F18)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "LEVEL ${level.levelNumber}",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = ArcadeTextDim
                            )
                        }
                    }
                },
                actions = {
                    // Coins Pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = ArcadeCardElevated,
                        border = BorderStroke(1.dp, ArcadeBorder),
                        modifier = Modifier
                            .clickable { onEarnCoinsClick() }
                            .testTag("play_earn_coins_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🪙", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "$coins",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                color = ArcadeText
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "+",
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Hint Button in Top Bar
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = ArcadeGold,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(34.dp)
                            .clickable { onOpenHint() }
                            .testTag("top_hint_button")
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.linearGradient(listOf(ArcadeFlame, ArcadeGold))),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Lightbulb,
                                contentDescription = "Hints",
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ArcadeBg)
            )
        },
        containerColor = ArcadeBg
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(6.dp))

                // Logo Area Box
                LogoCard(
                    logoKey = level.logoKey,
                    imageUrl = level.imageUrl,
                    clueText = level.hintSentence.ifBlank { "GUESS THE BRAND" },
                    categoryName = level.packId,
                    cardSize = 175.dp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Answer Slots Row
                LetterSlotsRow(
                    slots = slots,
                    slotState = slotState,
                    shakeTrigger = shakeTrigger,
                    onSlotClick = onSlotClick,
                    modifier = Modifier.fillMaxWidth()
                )

                // Celebration Toast Banner if Correct
                AnimatedVisibility(
                    visible = slotState == SlotState.CORRECT,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = ArcadeNeonGreen,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CORRECT! +50 XP",
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }

            // Scrambled Letter Bank & Controls at Bottom
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LetterBank(
                    tiles = bankTiles,
                    onTileClick = onBankTileClick,
                    onShuffle = onShuffle,
                    onClearAll = onClearAll
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Action Button: Hint Superpower Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onOpenHint,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ArcadeCardElevated),
                        border = BorderStroke(1.dp, ArcadeBorder),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("hint_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Lightbulb,
                                contentDescription = null,
                                tint = ArcadeGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (freeHintAvailable) "FREE HINT" else "HINT • 40 🪙",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp,
                                color = if (freeHintAvailable) ArcadeNeonGreen else ArcadeText
                            )
                        }
                    }
                }
            }
        }
    }

    // Hint Dialog
    if (showHintDialog) {
        HintDialog(
            freeHintAvailable = freeHintAvailable,
            coinsBalance = coins,
            onFreeHintClick = onUseFreeHint,
            onAdHintRemoveLetters = onAdHintRemoveLetters,
            onAdHintRevealLetter = onAdHintRevealLetter,
            onCoinHintClick = onUseCoinHint,
            onDismiss = onCloseHint
        )
    }

    // Level Complete Dialog
    if (showLevelComplete) {
        LevelCompleteDialog(
            level = level,
            hasNextLevel = level.levelNumber < 10,
            onNextLevel = onNextLevel,
            onBackToGrid = onBack,
            onDoubleCoinsAd = onDoubleCoinsAd
        )
    }
}
