package com.example.ui.screens

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
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.ui.theme.AmberStar
import com.example.ui.theme.WarmBg
import com.example.ui.theme.WarmBorder
import com.example.ui.theme.WarmBorderBright
import com.example.ui.theme.WarmSurface
import com.example.ui.theme.WarmSurface2
import com.example.ui.theme.WarmText
import com.example.ui.theme.WarmTextDim
import com.example.ui.theme.WireAmber
import com.example.ui.theme.WireSage
import com.example.ui.theme.WireTeal
import com.example.ui.theme.WireTealDim

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
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Sleek progress bar
                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .weight(1f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = WireTeal,
                            trackColor = WarmBorder
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        // Game Count e.g. "Logo 3/10"
                        Text(
                            text = "${level.levelNumber}/10",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmTextDim
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close",
                            tint = WarmTextDim,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                actions = {
                    // Coins Pill
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFFEF3C7),
                        border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                        modifier = Modifier
                            .clickable { onEarnCoinsClick() }
                            .testTag("play_earn_coins_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MonetizationOn,
                                contentDescription = "Coins",
                                tint = AmberStar,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "$coins",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFFB45309)
                            )
                        }
                    }

                    // Hint button with badge
                    IconButton(
                        onClick = onOpenHint,
                        modifier = Modifier.testTag("hint_button")
                    ) {
                        if (freeHintAvailable) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = WireSage,
                                        modifier = Modifier.size(7.dp)
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Lightbulb,
                                    contentDescription = "Hints",
                                    tint = WireAmber,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Rounded.Lightbulb,
                                contentDescription = "Hints",
                                tint = WireAmber,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmBg)
            )
        },
        containerColor = WarmBg
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
                Spacer(modifier = Modifier.height(4.dp))

                // Logo Area Box (Wireframe logo-box styling)
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = WarmSurface2,
                    border = BorderStroke(2.dp, WarmBorderBright),
                    modifier = Modifier
                        .size(160.dp)
                        .testTag("logo_display_card")
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LogoCard(
                            logoKey = level.logoKey,
                            imageUrl = level.imageUrl,
                            cardSize = 150.dp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Logo hint display: Hint sentence in warm surface
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = WarmSurface,
                    border = BorderStroke(1.dp, WarmBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Hint: ${level.hintSentence}",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = WarmTextDim,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Answer Slots Row
                LetterSlotsRow(
                    slots = slots,
                    slotState = slotState,
                    shakeTrigger = shakeTrigger,
                    onSlotClick = onSlotClick,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))
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

                Spacer(modifier = Modifier.height(8.dp))

                // Game action buttons: Hint & Clear & Shuffle row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onOpenHint,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = WarmSurface2,
                            contentColor = WarmText
                        ),
                        border = BorderStroke(1.dp, WarmBorder),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Hint",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = onClearAll,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = WarmSurface2,
                            contentColor = WarmText
                        ),
                        border = BorderStroke(1.dp, WarmBorder),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Clear",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = onShuffle,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WireTeal,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1.2f)
                    ) {
                        Text(
                            text = "Shuffle",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    // Hint Dialog (Preserving ad hint & coins logic)
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

    // Level Complete Dialog (Wireframe Screen 4)
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
