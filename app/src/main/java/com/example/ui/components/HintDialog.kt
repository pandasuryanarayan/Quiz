package com.example.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoFixHigh
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeBorderBright
import com.example.ui.theme.ArcadeCard
import com.example.ui.theme.ArcadeCardElevated
import com.example.ui.theme.ArcadeCardSecondary
import com.example.ui.theme.ArcadeFlame
import com.example.ui.theme.ArcadeGold
import com.example.ui.theme.ArcadeNeonCyan
import com.example.ui.theme.ArcadeNeonGreen
import com.example.ui.theme.ArcadePurple
import com.example.ui.theme.ArcadeText
import com.example.ui.theme.ArcadeTextDim
import com.example.ui.theme.ArcadeTextDisabled
import com.example.ui.theme.ArcadeTextMuted

@Composable
fun HintDialog(
    freeHintAvailable: Boolean,
    coinsBalance: Int,
    onFreeHintClick: () -> Unit,
    onAdHintRemoveLetters: () -> Unit,
    onAdHintRevealLetter: () -> Unit,
    onCoinHintClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = ArcadeCard,
            border = BorderStroke(1.dp, ArcadeBorderBright),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .testTag("hint_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                // Top Handle Pill
                Box(
                    modifier = Modifier
                        .size(width = 36.dp, height = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.18f))
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ArcadeGold,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.linearGradient(listOf(ArcadeFlame, ArcadeGold))),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Lightbulb,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Need a Boost?",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ArcadeText
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = ArcadePurple.copy(alpha = 0.25f),
                                    border = BorderStroke(0.5.dp, ArcadePurple.copy(alpha = 0.4f))
                                ) {
                                    Text(
                                        text = "POWER-UPS",
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 8.5.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 0.8.sp,
                                        color = ArcadeNeonCyan,
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Choose your superpower • Keep streak alive",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = ArcadeTextDim
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close",
                            tint = ArcadeTextDim,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Option 1: Free First Letter
                PowerUpCard(
                    title = "First Letter Reveal",
                    subtitle = if (freeHintAvailable) "Reveals the 1st letter of the brand" else "Already used on this level",
                    icon = Icons.Rounded.AutoFixHigh,
                    iconTint = ArcadeNeonGreen,
                    badgeText = if (freeHintAvailable) "FREE" else "USED",
                    badgeColor = if (freeHintAvailable) ArcadeNeonGreen else ArcadeTextDisabled,
                    badgeTextColor = if (freeHintAvailable) Color.Black else ArcadeTextDim,
                    isEnabled = freeHintAvailable,
                    onClick = onFreeHintClick,
                    tag = "free_hint_option"
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Option 2: Remove 3 Wrong Letters (Watch Ad)
                PowerUpCard(
                    title = "Remove 3 Letters",
                    subtitle = "Eliminates 3 decoy letters from the keyboard",
                    icon = Icons.Rounded.DeleteSweep,
                    iconTint = ArcadeNeonCyan,
                    badgeText = "▶ WATCH AD",
                    badgeColor = ArcadeNeonCyan,
                    badgeTextColor = Color.Black,
                    isEnabled = true,
                    onClick = onAdHintRemoveLetters,
                    tag = "ad_hint_remove_letters_option"
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Option 3: Reveal Next Letter (Watch Ad)
                PowerUpCard(
                    title = "Reveal Next Letter",
                    subtitle = "Places the next correct letter in position",
                    icon = Icons.Rounded.FlashOn,
                    iconTint = ArcadePurple,
                    badgeText = "▶ WATCH AD",
                    badgeColor = Color(0xFFA78BFA),
                    badgeTextColor = Color.Black,
                    isEnabled = true,
                    onClick = onAdHintRevealLetter,
                    tag = "ad_hint_reveal_letter_option"
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Option 4: Instant Solve with Coins
                val canAffordCoins = coinsBalance >= 40
                PowerUpCard(
                    title = "Instant Reveal",
                    subtitle = "Instantly fills and completes the puzzle",
                    icon = Icons.Rounded.Lightbulb,
                    iconTint = ArcadeGold,
                    badgeText = "🪙 40",
                    badgeColor = if (canAffordCoins) ArcadeGold else ArcadeTextDisabled,
                    badgeTextColor = if (canAffordCoins) Color.Black else ArcadeTextDim,
                    isEnabled = canAffordCoins,
                    onClick = onCoinHintClick,
                    tag = "coin_hint_option"
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Balance Footer
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🪙", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "Balance: $coinsBalance Coins",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ArcadeText
                        )
                    }

                    Text(
                        text = "WATCH ADS TO EARN",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        color = ArcadeTextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun PowerUpCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    badgeText: String,
    badgeColor: Color,
    badgeTextColor: Color,
    isEnabled: Boolean,
    onClick: () -> Unit,
    tag: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isEnabled) ArcadeCardSecondary else ArcadeCardElevated,
        border = BorderStroke(1.dp, if (isEnabled) ArcadeBorder else ArcadeBorderBright.copy(alpha = 0.2f)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled) { onClick() }
            .testTag(tag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isEnabled) iconTint.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.05f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isEnabled) iconTint else ArcadeTextDisabled,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = title,
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isEnabled) ArcadeText else ArcadeTextDim
                    )
                    Text(
                        text = subtitle,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Normal,
                        color = ArcadeTextMuted,
                        lineHeight = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = badgeColor
            ) {
                Text(
                    text = badgeText,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 0.5.sp,
                    color = badgeTextColor,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
