package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.rounded.AutoFixHigh
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.AmberStar
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.theme.TailwindBlue

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
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("hint_dialog")
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFFEF3C7),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Rounded.Lightbulb,
                                    contentDescription = null,
                                    tint = AmberStar,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Need a Hint?",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Close hint menu",
                            tint = Slate400
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Option 1: Free Hint (reveals first letter)
                HintOptionCard(
                    title = "Free First Letter",
                    subtitle = if (freeHintAvailable) "Reveals the first letter of the logo" else "Already used on this logo",
                    icon = Icons.Rounded.AutoFixHigh,
                    badgeText = if (freeHintAvailable) "FREE" else "USED",
                    badgeColor = if (freeHintAvailable) EmeraldSuccess else Slate400,
                    isEnabled = freeHintAvailable,
                    onClick = onFreeHintClick,
                    modifier = Modifier.testTag("free_hint_option")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Option 2: Ad Hint - Remove 3 wrong letters
                HintOptionCard(
                    title = "Remove 3 Wrong Letters",
                    subtitle = "Eliminate 3 incorrect scrambled tiles",
                    icon = Icons.Rounded.Videocam,
                    badgeText = "WATCH AD",
                    badgeColor = TailwindBlue,
                    isEnabled = true,
                    onClick = onAdHintRemoveLetters,
                    modifier = Modifier.testTag("ad_hint_remove_letters_option")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Option 3: Ad Hint - Reveal a random correct letter
                HintOptionCard(
                    title = "Reveal Next Letter",
                    subtitle = "Places the next correct character into the slot",
                    icon = Icons.Rounded.Videocam,
                    badgeText = "WATCH AD",
                    badgeColor = TailwindBlue,
                    isEnabled = true,
                    onClick = onAdHintRevealLetter,
                    modifier = Modifier.testTag("ad_hint_reveal_letter_option")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Option 4: Spend 40 coins to reveal letter
                val canAffordCoins = coinsBalance >= 40
                HintOptionCard(
                    title = "Instant Reveal (-40 Coins)",
                    subtitle = "Balance: $coinsBalance coins",
                    icon = Icons.Rounded.MonetizationOn,
                    badgeText = "40 COINS",
                    badgeColor = AmberStar,
                    isEnabled = canAffordCoins,
                    onClick = onCoinHintClick,
                    modifier = Modifier.testTag("coin_hint_option")
                )
            }
        }
    }
}

@Composable
private fun HintOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    badgeText: String,
    badgeColor: Color,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isEnabled) Color(0xFFF8FAFC) else Color(0xFFF1F5F9),
        border = BorderStroke(1.dp, if (isEnabled) Slate200 else Color(0xFFE2E8F0)),
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = isEnabled) { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = if (isEnabled) badgeColor.copy(alpha = 0.12f) else Slate200,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isEnabled) badgeColor else Slate400,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if (isEnabled) Slate900 else Slate400
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = if (isEnabled) Slate700 else Slate400,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isEnabled) badgeColor else Slate400
            ) {
                Text(
                    text = badgeText,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
