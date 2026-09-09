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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
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
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.theme.TailwindBlue
import com.example.ui.viewmodel.QuizViewModel

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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Level ${level.levelNumber}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Text(
                            text = "${level.answer.length} Letters",
                            fontSize = 11.sp,
                            color = Slate500,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("back_button")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back to Level Grid",
                            tint = Slate700
                        )
                    }
                },
                actions = {
                    // Coins Pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFEF3C7),
                        border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                        modifier = Modifier
                            .padding(end = 6.dp)
                            .clickable { onEarnCoinsClick() }
                            .testTag("play_earn_coins_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MonetizationOn,
                                contentDescription = "Coins",
                                tint = AmberStar,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$coins",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFFB45309)
                            )
                        }
                    }

                    // Lightbulb Hint Button
                    IconButton(
                        onClick = onOpenHint,
                        modifier = Modifier.testTag("hint_button")
                    ) {
                        if (freeHintAvailable) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = EmeraldSuccess,
                                        modifier = Modifier.size(8.dp)
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Lightbulb,
                                    contentDescription = "Hints",
                                    tint = AmberStar,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Rounded.Lightbulb,
                                contentDescription = "Hints",
                                tint = AmberStar,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CleanWhite)
            )
        },
        containerColor = Color(0xFFF8FAFC)
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Centered Visual Logo Prompt Card
                LogoCard(
                    logoKey = level.logoKey,
                    cardSize = 180.dp,
                    modifier = Modifier.testTag("logo_display_card")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Hint sentence bubble
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Info,
                            contentDescription = null,
                            tint = TailwindBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = level.hintSentence,
                            fontSize = 12.sp,
                            color = Slate700,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Blank Letter Slots Row
                LetterSlotsRow(
                    slots = slots,
                    slotState = slotState,
                    shakeTrigger = shakeTrigger,
                    onSlotClick = onSlotClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Scrambled Letter Bank at Bottom
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LetterBank(
                    tiles = bankTiles,
                    onTileClick = onBankTileClick,
                    onShuffle = onShuffle,
                    onClearAll = onClearAll
                )
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
