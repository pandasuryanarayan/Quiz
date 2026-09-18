package com.example.ui.components

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Gavel
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.window.DialogProperties
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
fun LegalDisclaimerDialog(
    isAgreementMode: Boolean = false,
    onAgree: () -> Unit = {},
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = !isAgreementMode
        )
    ) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = ArcadeCard,
            border = BorderStroke(1.dp, ArcadeBorderBright),
            modifier = modifier
                .fillMaxWidth(0.92f)
                .widthIn(max = 520.dp)
                .heightIn(max = 620.dp)
                .testTag("legal_disclaimer_dialog")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Rainbow Top Gradient Strip
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(ArcadeFlame, ArcadeGold, ArcadeNeonGreen, ArcadePurple)
                            )
                        )
                )

                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ArcadeFlame,
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.linearGradient(listOf(ArcadeFlame, ArcadeGold))),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Description,
                                    contentDescription = null,
                                    tint = ArcadeCanvas,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Legal & Trademark Notice",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = ArcadeText
                            )
                            Text(
                                text = "QUEST LORE • FAIR USE DOCTRINE",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = ArcadeFlame
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

                // Scrollable Lore Body
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp)
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    LoreNoticeCard(
                        title = "Brand Ownership",
                        text = "All brand names, trademarks, logos, and registered visual marks displayed in this application are the exclusive property of their respective trademark holders and corporate owners.",
                        icon = Icons.Rounded.Shield,
                        accent = ArcadeFlame
                    )

                    LoreNoticeCard(
                        title = "Fair Use Doctrine",
                        text = "The depiction of these marks is conducted strictly under the nominative fair use doctrine of United States Trademark Law (15 U.S.C. § 1125(c)(3)) and international trademark provisions for educational trivia, cultural recognition, and descriptive puzzle identification.",
                        icon = Icons.Rounded.Gavel,
                        accent = ArcadeGold
                    )

                    LoreNoticeCard(
                        title = "No Affiliation or Endorsement",
                        text = "The creators, operators, and contributors of Logo Quiz have no direct affiliation, commercial partnership, sponsorship, or endorsement with any corporation, brand, institution, or trademark holder depicted within this game.",
                        icon = Icons.Rounded.Public,
                        accent = ArcadeNeonCyan
                    )

                    LoreNoticeCard(
                        title = "As-Is Entertainment Warranty",
                        text = "This quiz is provided entirely on an 'AS-IS' and 'AS-AVAILABLE' basis for recreational gaming. Brand information is curated for trivia enjoyment.",
                        icon = Icons.Rounded.Security,
                        accent = ArcadeNeonGreen
                    )

                    // Warning / Tribute Callout Banner
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0x33FF6B35),
                        border = BorderStroke(1.dp, ArcadeFlame.copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "⚠️", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "This is a fan-made tribute. We celebrate world-famous brands and culture, we do not claim them.",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = ArcadeText,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Bottom Action Footer
                Surface(
                    color = ArcadeCardElevated,
                    border = BorderStroke(1.dp, ArcadeBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = {
                                if (isAgreementMode) onAgree() else onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ArcadeNeonGreen),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("legal_confirm_button")
                        ) {
                            Text(
                                text = "✓ UNDERSTOOD, LET'S PLAY",
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoreNoticeCard(
    title: String,
    text: String,
    icon: ImageVector,
    accent: Color
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = ArcadeCardSecondary,
        border = BorderStroke(1.dp, ArcadeBorderSubtle),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = accent.copy(alpha = 0.15f),
                modifier = Modifier.size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = ArcadeText
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = text,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = ArcadeTextDim,
                    lineHeight = 15.sp
                )
            }
        }
    }
}
