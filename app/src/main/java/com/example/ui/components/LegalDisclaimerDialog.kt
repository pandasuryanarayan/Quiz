package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Gavel
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Public
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.WarmBg
import com.example.ui.theme.WarmBorder
import com.example.ui.theme.WarmBorderBright
import com.example.ui.theme.WarmSurface2
import com.example.ui.theme.WarmText
import com.example.ui.theme.WarmTextDim
import com.example.ui.theme.WireAmber
import com.example.ui.theme.WireAmberDim
import com.example.ui.theme.WireTeal
import com.example.ui.theme.WireTealDim

/**
 * Dialog displaying legal disclaimer, brand trademark ownership disclosures,
 * indicative representation notices, and nominative fair use compliance.
 *
 * @param isAgreementMode If true, requires the user to scroll to the bottom to enable "I Agree".
 * @param onAgree Invoked when user agrees to the disclaimer terms.
 * @param onDismiss Invoked when user dismisses or declines.
 */
@Composable
fun LegalDisclaimerDialog(
    isAgreementMode: Boolean = false,
    onAgree: () -> Unit = {},
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var hasScrolledToBottom by remember { mutableStateOf(false) }

    // Track scroll completion: once scrolled near bottom or if content fits without scrolling
    LaunchedEffect(scrollState.value, scrollState.maxValue) {
        if (scrollState.maxValue > 0) {
            if (scrollState.value >= scrollState.maxValue - 24) {
                hasScrolledToBottom = true
            }
        } else {
            // Fits within viewport without scrolling (e.g., large tablets)
            hasScrolledToBottom = true
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = !isAgreementMode
        )
    ) {
        Surface(
            modifier = modifier
                .padding(horizontal = 20.dp, vertical = 28.dp)
                .widthIn(max = 500.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 12.dp,
            border = BorderStroke(1.dp, WarmBorderBright)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                // Header Bar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = WireTealDim,
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Rounded.Gavel,
                                    contentDescription = null,
                                    tint = WireTeal,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = WireAmberDim,
                                modifier = Modifier.padding(bottom = 2.dp)
                            ) {
                                Text(
                                    text = "LEGAL & TRADEMARK NOTICE",
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WireAmber,
                                    letterSpacing = 0.5.sp,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "Terms & Disclaimer",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = WarmText
                            )
                        }
                    }

                    if (!isAgreementMode) {
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.testTag("disclaimer_close_icon_button")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close",
                                tint = WarmTextDim
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = WarmBorder, thickness = 1.dp)

                // Scrollable Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 440.dp)
                        .weight(1f, fill = false)
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    // Region Chips Banner
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = WarmSurface2,
                        border = BorderStroke(1.dp, WarmBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Public,
                                contentDescription = null,
                                tint = WireTeal,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Covering European, American, Indian, Chinese & Global Entities",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = WarmText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    DisclaimerSection(
                        icon = Icons.Rounded.Shield,
                        title = "1. Brand Ownership & Proprietary Rights",
                        body = "All brand logos, trademarks, registered trademarks, service marks, trade dress, and product names referenced, displayed, or depicted in this application are the sole and exclusive intellectual property of their respective corporate owners, parent enterprises, and authorized affiliates.\n\nThe independent developer of this application has NO ownership rights, copyright claims, or proprietary interest in any of the displayed logos, trade names, or brand assets."
                    )

                    DisclaimerSection(
                        icon = Icons.Rounded.Gavel,
                        title = "2. Indicative & Stylized Representation",
                        body = "Logos and graphical emblems presented in this trivia quiz are purely indicative and stylized for puzzle identification and educational gameplay. They may differ in color fidelity, geometry, or typography and may not match the official or current corporate design guidelines of the respective brand holders."
                    )

                    DisclaimerSection(
                        icon = Icons.Rounded.Public,
                        title = "3. Global Enterprise Scope",
                        body = "This trivia game incorporates brand recognition challenges from diverse global jurisdictions, including but not limited to:\n" +
                                "• European Enterprises: Iconic automotive, luxury fashion, and digital services (e.g., Ferrari, Porsche, BMW, Mercedes-Benz, Audi, Renault, Alfa Romeo, Spotify, etc.)\n" +
                                "• American Corporations: Global technology leaders, consumer brands, and fast-food giants (e.g., Apple, Google, Microsoft, Amazon, Tesla, Nike, McDonald's, KFC, etc.)\n" +
                                "• Indian Heritage & Modern Conglomerates: Automotive manufacturers, food & beverage titans, and dairy cooperatives (e.g., Tata Motors, Mahindra, Haldiram's, Parle, ITC, Mother Dairy, etc.)\n" +
                                "• Chinese Multinational Mobility & Tech: Electric mobility innovators, smart manufacturing, and consumer tech (e.g., BYD, Geely, NIO, XPeng, Zeekr, etc.)\n" +
                                "• As well as prominent brands from Japan, South Korea, and around the world."
                    )

                    DisclaimerSection(
                        icon = Icons.Rounded.Shield,
                        title = "4. Nominative Fair Use & Non-Affiliation",
                        body = "The use of low-resolution or stylized brand marks in this application is strictly for non-commercial trivia, cultural recognition, and public education under the doctrine of Nominative Fair Use and comparable international fair dealing provisions.\n\nThis application is NOT affiliated with, sponsored by, endorsed by, or in partnership with any of the companies, brands, or trademark holders featured."
                    )

                    DisclaimerSection(
                        icon = Icons.Rounded.Check,
                        title = "5. Inquiries & Takedown Requests",
                        body = "If you are a verified trademark owner or legal representative and have questions, modification requests, or takedown inquiries regarding any mark shown, please submit your notice via the application feedback or developer contact. Inquiries will be addressed promptly in good faith."
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                HorizontalDivider(color = WarmBorder, thickness = 1.dp)

                // Scroll Prompt if agreement mode and user hasn't scrolled to bottom yet
                if (isAgreementMode && !hasScrolledToBottom) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null,
                            tint = WireAmber,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Please scroll down to the bottom to agree",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Medium,
                            color = WireAmber
                        )
                    }
                }

                // Action Buttons
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    if (isAgreementMode) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(
                                onClick = onDismiss,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, WarmBorderBright),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = WarmTextDim
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .testTag("disclaimer_decline_button")
                            ) {
                                Text(
                                    text = "Cancel",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Button(
                                onClick = onAgree,
                                enabled = hasScrolledToBottom,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = WireTeal,
                                    contentColor = Color.White,
                                    disabledContainerColor = WarmSurface2,
                                    disabledContentColor = WarmTextDim
                                ),
                                modifier = Modifier
                                    .weight(1.3f)
                                    .height(48.dp)
                                    .testTag("disclaimer_agree_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (hasScrolledToBottom) {
                                        Icon(
                                            imageVector = Icons.Rounded.Check,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                    }
                                    Text(
                                        text = "I Agree",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    } else {
                        Button(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WireTeal,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("disclaimer_understood_button")
                        ) {
                            Text(
                                text = "Understood",
                                fontSize = 14.sp,
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
private fun DisclaimerSection(
    icon: ImageVector,
    title: String,
    body: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = WarmBg,
        border = BorderStroke(1.dp, WarmBorder),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = WireTeal,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    fontSize = 13.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = WarmText
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = body,
                fontSize = 12.sp,
                color = WarmTextDim,
                lineHeight = 17.sp
            )
        }
    }
}
