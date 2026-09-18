package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.ui.theme.ArcadeTextMuted
import kotlinx.coroutines.delay

enum class AdPurpose {
    UNLOCK_LEVEL,
    REMOVE_WRONG_LETTERS,
    REVEAL_LETTER,
    EARN_COINS
}

@Composable
fun AdPromptDialog(
    purpose: AdPurpose,
    levelNumber: Int = 0,
    onConfirmWatch: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = ArcadeCard,
            border = BorderStroke(1.dp, ArcadeBorderBright),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .testTag("ad_prompt_dialog")
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Squircle
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = ArcadeFlame,
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.linearGradient(listOf(ArcadeFlame, ArcadeGold)))
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Videocam,
                            contentDescription = "Rewarded Ad",
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                val title = when (purpose) {
                    AdPurpose.UNLOCK_LEVEL -> "Unlock Logo $levelNumber"
                    AdPurpose.REMOVE_WRONG_LETTERS -> "Remove 3 Letters"
                    AdPurpose.REVEAL_LETTER -> "Reveal Next Letter"
                    AdPurpose.EARN_COINS -> "Earn +50 Free Coins"
                }

                val subtitle = when (purpose) {
                    AdPurpose.UNLOCK_LEVEL -> "Logos 6 and above are locked. Watch a short 5-second video to permanently unlock this logo!"
                    AdPurpose.REMOVE_WRONG_LETTERS -> "Watch a quick video to eliminate 3 distracting decoy letters from the keyboard."
                    AdPurpose.REVEAL_LETTER -> "Watch a short video to place the next correct letter in your answer slots."
                    AdPurpose.EARN_COINS -> "Watch a short video to instantly claim 50 free game coins!"
                }

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = ArcadeText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = subtitle,
                    fontSize = 12.5.sp,
                    color = ArcadeTextDim,
                    textAlign = TextAlign.Center,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = ArcadeCardSecondary,
                    border = BorderStroke(0.5.dp, ArcadeBorder)
                ) {
                    Text(
                        text = "LEVELPLAY · REWARDED SPONSOR",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        color = ArcadeNeonCyan,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onConfirmWatch,
                    colors = ButtonDefaults.buttonColors(containerColor = ArcadeNeonGreen),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("watch_ad_button")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "WATCH VIDEO TO UNLOCK",
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        letterSpacing = 0.5.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, ArcadeBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("cancel_ad_button")
                ) {
                    Text(
                        text = "Not Now",
                        color = ArcadeTextDim,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun RewardedAdPlayerModal(
    purpose: AdPurpose,
    onAdCompleted: () -> Unit,
    onClose: () -> Unit
) {
    var secondsLeft by remember { mutableIntStateOf(5) }
    var isFinished by remember { mutableStateOf(false) }
    var showWarningDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            delay(1000L)
            secondsLeft -= 1
        }
        isFinished = true
    }

    Dialog(
        onDismissRequest = {
            if (!isFinished) showWarningDialog = true else onAdCompleted()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A10).copy(alpha = 0.96f))
                .testTag("rewarded_ad_player")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Header Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ArcadeCardElevated,
                        border = BorderStroke(1.dp, ArcadeBorder)
                    ) {
                        Text(
                            text = "LevelPlay · Rewarded Video",
                            color = ArcadeTextDim,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }

                    if (isFinished) {
                        IconButton(onClick = onAdCompleted) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close ad and claim reward",
                                tint = ArcadeText
                            )
                        }
                    } else {
                        Surface(
                            shape = CircleShape,
                            color = ArcadeCardElevated,
                            border = BorderStroke(1.dp, ArcadeBorder)
                        ) {
                            Text(
                                text = "Reward in ${secondsLeft}s",
                                color = ArcadeNeonCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                // Center Ad Creative Preview
                Surface(
                    shape = RoundedCornerShape(26.dp),
                    color = ArcadeCard,
                    border = BorderStroke(1.dp, ArcadeBorderBright),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = ArcadePurple,
                            modifier = Modifier.size(72.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.linearGradient(listOf(ArcadePurple, ArcadeNeonCyan))),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier.size(42.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Super Trivia Quest 3D",
                            color = ArcadeText,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Test your brain with 10,000+ brand & logo puzzles! Free on Google Play.",
                            color = ArcadeTextDim,
                            fontSize = 12.5.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        if (!isFinished) {
                            LinearProgressIndicator(
                                progress = { (5 - secondsLeft) / 5f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = ArcadeNeonGreen,
                                trackColor = Color(0xFF1E1E2E)
                            )
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = null,
                                    tint = ArcadeNeonGreen,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                val rewardLabel = when (purpose) {
                                    AdPurpose.EARN_COINS -> "+50 Coins Granted!"
                                    AdPurpose.UNLOCK_LEVEL -> "Logo Unlocked!"
                                    AdPurpose.REMOVE_WRONG_LETTERS -> "3 Letters Removed!"
                                    AdPurpose.REVEAL_LETTER -> "Letter Revealed!"
                                }
                                Text(
                                    text = rewardLabel,
                                    color = ArcadeNeonGreen,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }

                // Bottom Action Button
                if (isFinished) {
                    Button(
                        onClick = { onAdCompleted() },
                        colors = ButtonDefaults.buttonColors(containerColor = ArcadeNeonGreen),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("claim_ad_reward_button")
                    ) {
                        val buttonLabel = when (purpose) {
                            AdPurpose.EARN_COINS -> "CLAIM +50 COINS & CONTINUE"
                            AdPurpose.UNLOCK_LEVEL -> "PLAY UNLOCKED LEVEL"
                            else -> "CLAIM REWARD & CONTINUE"
                        }
                        Text(
                            text = buttonLabel,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = Color.Black
                        )
                    }
                } else {
                    OutlinedButton(
                        onClick = { showWarningDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, ArcadeBorder),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 8.dp)
                    ) {
                        Text("Skip (Lose Reward)", color = ArcadeTextMuted, fontSize = 12.sp)
                    }
                }
            }
        }

        if (showWarningDialog) {
            Dialog(onDismissRequest = { showWarningDialog = false }) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = ArcadeCard,
                    border = BorderStroke(1.dp, ArcadeBorderBright),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Skip Video Early?",
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp,
                            color = ArcadeText
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "If you close before the video ends, the stage or power-up will remain locked.",
                            fontSize = 12.5.sp,
                            color = ArcadeTextDim,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = {
                                    showWarningDialog = false
                                    onClose()
                                },
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, ArcadeBorder),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Close", color = Color(0xFFEF4444), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Button(
                                onClick = { showWarningDialog = false },
                                colors = ButtonDefaults.buttonColors(containerColor = ArcadeNeonGreen),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Resume", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LevelPlayAdLoadingModal(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = ArcadeCard,
            border = BorderStroke(1.dp, ArcadeBorderBright),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = ArcadeNeonCyan,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Loading LevelPlay Ad...",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = ArcadeText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Placement: LevelPlay Rewarded\nAd Unit: wljj57ixzqvzvcxo",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = ArcadeTextDim,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, ArcadeBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel", color = ArcadeTextDim, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun LevelPlayAdErrorModal(
    errorMessage: String,
    onRetry: () -> Unit,
    onFallback: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = ArcadeCard,
            border = BorderStroke(1.dp, ArcadeBorderBright),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0x33EF4444),
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Ad Notice",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = ArcadeText
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = errorMessage,
                    fontSize = 12.sp,
                    color = ArcadeTextDim,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(containerColor = ArcadeNeonGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text("Retry Ad", fontWeight = FontWeight.Black, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = onFallback,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, ArcadeBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text("Watch Simulation Player", color = ArcadeText, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(4.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close", color = ArcadeTextDim, fontSize = 13.sp)
                }
            }
        }
    }
}
