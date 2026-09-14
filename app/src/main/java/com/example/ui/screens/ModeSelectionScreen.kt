package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.LockOpen
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppMode
import com.example.ui.components.AdminPasswordDialog
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

@Composable
fun ModeSelectionScreen(
    onSelectMode: (AppMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var showAdminPasswordDialog by remember { mutableStateOf(false) }

    if (showAdminPasswordDialog) {
        AdminPasswordDialog(
            onSuccess = {
                showAdminPasswordDialog = false
                onSelectMode(AppMode.ADMIN)
            },
            onDismiss = {
                showAdminPasswordDialog = false
            }
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = WarmBg
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // App Emblem
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = WireTeal,
                modifier = Modifier.size(76.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.background(
                        Brush.linearGradient(
                            listOf(WireTeal, WireAmber)
                        )
                    )
                ) {
                    Text(
                        text = "Q",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 40.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Logo Quiz",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = WarmText,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Select your experience mode to get started",
                fontSize = 13.sp,
                color = WarmTextDim,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // USER MODE CARD
            Card(
                onClick = { onSelectMode(AppMode.USER) },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                border = BorderStroke(1.5.dp, WarmBorderBright),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mode_user_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(WireTeal.copy(alpha = 0.12f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Person,
                                    contentDescription = null,
                                    tint = WireTeal,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "User Mode",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WarmText
                                )
                                Text(
                                    text = "Standard Player Experience",
                                    fontSize = 11.sp,
                                    color = WarmTextDim
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = WireTeal.copy(alpha = 0.1f),
                            border = BorderStroke(1.dp, WireTeal.copy(alpha = 0.3f))
                        ) {
                            Text(
                                text = "Standard",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = WireTeal,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Authentic player journey with progressive logo unlocking, coins, hints, and rewarded challenges.",
                        fontSize = 12.sp,
                        color = WarmTextDim,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        ModeFeatureItem(text = "Sequential logo progression")
                        ModeFeatureItem(text = "Coin balance & hint management")
                        ModeFeatureItem(text = "Standard gameplay rules")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onSelectMode(AppMode.USER) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = WireTeal),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("mode_user_button")
                    ) {
                        Text(
                            text = "Start User Mode",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Rounded.ArrowForward,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ADMIN MODE CARD
            Card(
                onClick = { showAdminPasswordDialog = true },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                border = BorderStroke(1.5.dp, Color(0xFF93C5FD)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mode_admin_card")
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(Color(0xFF2563EB), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Admin Mode",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WarmText
                                )
                                Text(
                                    text = "Tester & QA Mode",
                                    fontSize = 11.sp,
                                    color = Color(0xFF2563EB),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFEF3C7),
                            border = BorderStroke(1.dp, Color(0xFFFDE68A))
                        ) {
                            Text(
                                text = "All Unlocked",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Full unrestricted testing mode where all logos across all categories are unlocked for instant testing.",
                        fontSize = 12.sp,
                        color = WarmTextDim,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        ModeFeatureItem(text = "All logos unlocked immediately", iconTint = Color(0xFF2563EB))
                        ModeFeatureItem(text = "Jump to any logo to test graphics & answers", iconTint = Color(0xFF2563EB))
                        ModeFeatureItem(text = "No lock dialogs or sequential restrictions", iconTint = Color(0xFF2563EB))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showAdminPasswordDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D4ED8)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("mode_admin_button")
                    ) {
                        Text(
                            text = "Launch Admin Mode",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Rounded.LockOpen,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Tip: You can switch modes anytime from the main screen",
                fontSize = 11.sp,
                color = WarmTextDim,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ModeFeatureItem(
    text: String,
    iconTint: Color = WireSage
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.CheckCircle,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 11.sp,
            color = WarmText,
            fontWeight = FontWeight.Medium
        )
    }
}
