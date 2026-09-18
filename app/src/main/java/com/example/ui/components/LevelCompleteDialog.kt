package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.QuizLevel
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeBorderBright
import com.example.ui.theme.ArcadeBorderSubtle
import com.example.ui.theme.ArcadeCanvas
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
import com.example.ui.theme.ArcadeTextMuted

@Composable
fun LevelCompleteDialog(
    level: QuizLevel,
    hasNextLevel: Boolean,
    onNextLevel: () -> Unit,
    onBackToGrid: () -> Unit,
    onDoubleCoinsAd: () -> Unit
) {
    var animateIn by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animateIn = true
    }

    val scale by animateFloatAsState(
        targetValue = if (animateIn) 1f else 0.85f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "dialogScale"
    )

    Dialog(
        onDismissRequest = { /* Must choose an action */ },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = ArcadeCard,
            border = BorderStroke(1.dp, ArcadeBorderBright),
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale)
                .shadow(16.dp, RoundedCornerShape(26.dp))
                .padding(4.dp)
                .testTag("level_complete_dialog")
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Trophy / Neon Check Squircle
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = ArcadeNeonGreen,
                    modifier = Modifier.size(68.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(listOf(ArcadeNeonGreen, ArcadeNeonCyan))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Victory",
                            tint = Color.Black,
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "LEVEL CONQUERED!",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Black,
                    color = ArcadeText,
                    letterSpacing = (-0.5).sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = level.originalName.ifBlank { level.answer },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArcadeNeonGreen
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Rewards Row (Coins, XP, Stars)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ArcadeRewardPill(
                        value = "+50",
                        label = "COINS",
                        color = ArcadeGold,
                        modifier = Modifier.weight(1f)
                    )
                    ArcadeRewardPill(
                        value = "+100",
                        label = "XP",
                        color = ArcadePurple,
                        modifier = Modifier.weight(1f)
                    )
                    ArcadeRewardPill(
                        value = "3 ★",
                        label = "STARS",
                        color = ArcadeNeonGreen,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Double Coins Ad Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = ArcadeCardElevated,
                    border = BorderStroke(1.dp, ArcadeGold.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onDoubleCoinsAd() }
                        .testTag("double_coins_ad_button")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Videocam,
                                contentDescription = null,
                                tint = ArcadeGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "2X REWARD (+100 COINS)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp,
                                    color = ArcadeGold
                                )
                                Text(
                                    text = "Watch short 15s sponsored clip",
                                    fontSize = 10.sp,
                                    color = ArcadeTextDim
                                )
                            }
                        }

                        Surface(
                            shape = CircleShape,
                            color = ArcadeGold,
                            modifier = Modifier.size(26.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Rounded.PlayArrow,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Primary Next Level Button
                if (hasNextLevel) {
                    Button(
                        onClick = onNextLevel,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("next_level_button")
                    ) {
                        Text(
                            text = "NEXT LOGO →",
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Return to Pack / Grid
                OutlinedButton(
                    onClick = onBackToGrid,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, ArcadeBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("dialog_back_to_grid")
                ) {
                    Text(
                        text = "BACK TO PACK",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ArcadeTextDim
                    )
                }
            }
        }
    }
}

@Composable
private fun ArcadeRewardPill(
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = ArcadeCardSecondary,
        border = BorderStroke(1.dp, ArcadeBorderSubtle),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontFamily = FontFamily.Monospace,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                color = ArcadeTextMuted
            )
        }
    }
}
