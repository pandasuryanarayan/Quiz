package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
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
import com.example.ui.theme.WireSageDim
import com.example.ui.theme.WireTeal

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
            shape = RoundedCornerShape(24.dp),
            color = WarmSurface,
            border = BorderStroke(1.dp, WarmBorderBright),
            modifier = Modifier
                .fillMaxWidth()
                .scale(scale)
                .shadow(12.dp, RoundedCornerShape(24.dp))
                .padding(8.dp)
                .testTag("level_complete_dialog")
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Trophy & Title
                Text(
                    text = "🏆",
                    fontSize = 44.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Logo Complete!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = WarmText,
                    letterSpacing = (-0.5).sp
                )

                Text(
                    text = "Logo ${level.levelNumber} Solved",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    color = WarmTextDim
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Wireframe 3 Stat Cards (stat-card)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        value = "+50",
                        label = "COINS",
                        valueColor = WireTeal,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        value = "★★★",
                        label = "STARS",
                        valueColor = WireAmber,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        value = "100%",
                        label = "SCORE",
                        valueColor = WireSage,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Review Section (from Wireframe Screen 4)
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = WireSageDim,
                    border = BorderStroke(1.dp, WireSage.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .background(WireSage, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = level.originalName.ifBlank { level.answer },
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WarmText
                                )
                            }

                            Text(
                                text = "+50 coins",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = WireSage
                            )
                        }

                        if (level.triviaFact.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = level.triviaFact,
                                fontSize = 11.sp,
                                color = WarmTextDim,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Double Coins Ad Bonus Button
                OutlinedButton(
                    onClick = onDoubleCoinsAd,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFFEF3C7),
                        contentColor = Color(0xFFB45309)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("double_coins_ad_button")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Videocam,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Double Reward (+100 Coins)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Result Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onBackToGrid,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = WarmSurface2,
                            contentColor = WarmText
                        ),
                        border = BorderStroke(1.dp, WarmBorder),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("back_to_grid_button")
                    ) {
                        Text(
                            text = "Logos",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (hasNextLevel) {
                        Button(
                            onClick = onNextLevel,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WireTeal,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1.3f)
                                .testTag("next_level_button")
                        ) {
                            Text(
                                text = "Next Logo ›",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = WarmSurface2,
        border = BorderStroke(1.dp, WarmBorder),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = valueColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontFamily = FontFamily.Monospace,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                color = WarmTextDim
            )
        }
    }
}
