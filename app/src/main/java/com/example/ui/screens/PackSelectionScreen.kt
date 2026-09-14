package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PackProgressSummary
import com.example.data.UserProfileEntity
import com.example.ui.theme.AmberStar
import com.example.ui.theme.WarmBg
import com.example.ui.theme.WarmBorder
import com.example.ui.theme.WarmBorderBright
import com.example.ui.theme.WarmSurface
import com.example.ui.theme.WarmSurface2
import com.example.ui.theme.WarmText
import com.example.ui.theme.WarmTextDim
import com.example.ui.theme.WireAmber
import com.example.ui.theme.WireTeal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackSelectionScreen(
    packSummaries: List<PackProgressSummary>,
    userProfile: UserProfileEntity?,
    isAdminMode: Boolean = false,
    onSelectPack: (String) -> Unit,
    onQuickPlayClick: () -> Unit = {},
    onEarnCoinsClick: () -> Unit,
    onSwitchMode: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Text(
                            text = "Logo Quiz",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = WarmText,
                            letterSpacing = (-0.5).sp
                        )
                    }
                },
                actions = {
                    // Mode Switcher Pill (Keeps Admin / User Mode intact)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isAdminMode) Color(0xFFEFF6FF) else WarmSurface2,
                        border = BorderStroke(1.dp, if (isAdminMode) Color(0xFF93C5FD) else WarmBorder),
                        modifier = Modifier
                            .clickable { onSwitchMode() }
                            .testTag("switch_mode_pill")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isAdminMode) Icons.Rounded.AdminPanelSettings else Icons.Rounded.Person,
                                contentDescription = "Mode",
                                tint = if (isAdminMode) Color(0xFF1D4ED8) else WarmTextDim,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isAdminMode) "Admin" else "User",
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = if (isAdminMode) Color(0xFF1D4ED8) else WarmText
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Icon(
                                imageVector = Icons.Rounded.SwapHoriz,
                                contentDescription = "Switch",
                                tint = WarmTextDim,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Coins Pill (Ad trigger for +50 coins)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFEF3C7),
                        border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                        modifier = Modifier
                            .clickable { onEarnCoinsClick() }
                            .testTag("earn_coins_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
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
                                text = "${userProfile?.coins ?: 150}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color(0xFFB45309)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "+",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = AmberStar
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmSurface)
            )
        },
        containerColor = WarmBg
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Admin Mode Active Banner
            if (isAdminMode) {
                item {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFEFF6FF),
                        border = BorderStroke(1.dp, Color(0xFF93C5FD)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(Color(0xFF2563EB), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.AdminPanelSettings,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Admin Mode Active",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFF1E3A8A)
                                    )
                                    Text(
                                        text = "All logos unlocked for instant testing",
                                        fontSize = 11.sp,
                                        color = Color(0xFF2563EB)
                                    )
                                }
                            }
                            OutlinedButton(
                                onClick = onSwitchMode,
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, Color(0xFF2563EB)),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                                modifier = Modifier.testTag("switch_to_user_mode_button")
                            ) {
                                Text(
                                    text = "Switch",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF2563EB)
                                )
                            }
                        }
                    }
                }
            }

            // Quick Play Hero Card (Wireframe Top Feature)
            item {
                QuickPlayHeroCard(
                    onPlayClick = onQuickPlayClick
                )
            }

            // Section Heading: Categories
            item {
                Text(
                    text = "CATEGORIES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = WarmTextDim,
                    letterSpacing = 1.5.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                )
            }

            // Categories 2-Column Grid
            val chunkedPacks = packSummaries.chunked(2)
            chunkedPacks.forEach { rowPacks ->
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowPacks.forEach { summary ->
                            CategoryWireCard(
                                summary = summary,
                                onClick = { onSelectPack(summary.pack.id) },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("pack_card_${summary.pack.id}")
                            )
                        }
                        if (rowPacks.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun QuickPlayHeroCard(
    onPlayClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = WireTeal,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(18.dp))
            .clickable { onPlayClick() }
            .testTag("quick_play_card")
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(WireTeal, WireAmber)
                    )
                )
                .padding(18.dp)
        ) {
            // Subtle decorative circle overlay
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.12f))
                    .align(Alignment.TopEnd)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "ONE-TAP START",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Mixed Quick Play",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "10 random logos from all categories",
                    fontSize = 11.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(14.dp))

                // Play Badge on right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.22f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.35f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Play ›",
                                color = Color.White,
                                fontSize = 12.sp,
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
private fun CategoryWireCard(
    summary: PackProgressSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pack = summary.pack
    val primaryColor = Color(pack.primaryColorHex)
    val dimColor = Color(pack.dimColorHex)
    val progress = if (summary.totalLevels > 0) {
        summary.completedLevels.toFloat() / summary.totalLevels
    } else 0f

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = dimColor,
        border = BorderStroke(1.dp, primaryColor),
        modifier = modifier
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Category Icon Emoji
            Text(
                text = pack.emoji,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Category Title
            Text(
                text = pack.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = WarmText,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Category Progress Bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = primaryColor,
                trackColor = WarmBorder
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Solved Count
            Text(
                text = "${summary.completedLevels} / ${summary.totalLevels}",
                fontFamily = FontFamily.Monospace,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = WarmTextDim,
                textAlign = TextAlign.Center
            )
        }
    }
}
