package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.AppMode
import com.example.ui.components.AdminPasswordDialog
import com.example.ui.components.LegalDisclaimerDialog
import com.example.ui.theme.ArcadeBg
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeBorderBright
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
fun ModeSelectionScreen(
    onSelectMode: (AppMode) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAdminPasswordDialog by remember { mutableStateOf(false) }
    var showDisclaimerInfoDialog by remember { mutableStateOf(false) }
    var showAgreementDisclaimerDialog by remember { mutableStateOf(false) }

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

    if (showDisclaimerInfoDialog) {
        LegalDisclaimerDialog(
            isAgreementMode = false,
            onDismiss = { showDisclaimerInfoDialog = false }
        )
    }

    if (showAgreementDisclaimerDialog) {
        LegalDisclaimerDialog(
            isAgreementMode = true,
            onAgree = {
                showAgreementDisclaimerDialog = false
                onSelectMode(AppMode.USER)
            },
            onDismiss = {
                showAgreementDisclaimerDialog = false
            }
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = ArcadeBg
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            // Legal Lore icon in top right
            IconButton(
                onClick = { showDisclaimerInfoDialog = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(ArcadeCardElevated)
                    .testTag("help_disclaimer_button")
            ) {
                Icon(
                    imageVector = Icons.Rounded.Description,
                    contentDescription = "Legal & Trademark Disclaimer",
                    tint = ArcadeTextDim,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(
                modifier = Modifier
                    .widthIn(max = 420.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Glowing App Icon Squircle
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = ArcadeFlame,
                    border = BorderStroke(2.dp, ArcadeBorderBright),
                    modifier = Modifier.size(114.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    listOf(ArcadeFlame, ArcadeGold)
                                )
                            )
                            .padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.app_logo),
                            contentDescription = "Logo Quiz App Logo",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(22.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "LOGO QUIZ",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = ArcadeText,
                    letterSpacing = (-0.5).sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "101 Real Brand Logos • 8 Distinct Worlds",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = ArcadeTextDim,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(36.dp))

                // Start Playing Button
                Button(
                    onClick = { showAgreementDisclaimerDialog = true },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("start_playing_button")
                        .testTag("mode_user_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "START PLAYING",
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Disclaimer link
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable { showDisclaimerInfoDialog = true }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Subject to ",
                        fontSize = 11.5.sp,
                        color = ArcadeTextMuted
                    )
                    Text(
                        text = "Legal & Trademark Notice",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = ArcadeNeonCyan
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Admin Login Row
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = ArcadeCardSecondary,
                    border = BorderStroke(1.dp, ArcadeBorder),
                    modifier = Modifier
                        .clickable { showAdminPasswordDialog = true }
                        .testTag("mode_admin_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AdminPanelSettings,
                            contentDescription = null,
                            tint = ArcadeTextDim,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Admin Sandbox Login",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ArcadeTextDim
                        )
                    }
                }
            }
        }
    }
}
