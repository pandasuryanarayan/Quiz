package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Casino
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PackProgressSummary
import com.example.data.UserProfileEntity
import com.example.ui.components.LegalDisclaimerDialog
import com.example.ui.theme.ArcadeBg
import com.example.ui.theme.ArcadeBlue
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeBorderBright
import com.example.ui.theme.ArcadeBorderSubtle
import com.example.ui.theme.ArcadeCanvas
import com.example.ui.theme.ArcadeCard
import com.example.ui.theme.ArcadeCardElevated
import com.example.ui.theme.ArcadeCardSecondary
import com.example.ui.theme.ArcadeCyan
import com.example.ui.theme.ArcadeDarkBlue
import com.example.ui.theme.ArcadeDarkTeal
import com.example.ui.theme.ArcadeFlame
import com.example.ui.theme.ArcadeFlameDark
import com.example.ui.theme.ArcadeFlameSecondary
import com.example.ui.theme.ArcadeGold
import com.example.ui.theme.ArcadeGoldBright
import com.example.ui.theme.ArcadeGoldDark
import com.example.ui.theme.ArcadeGoldLight
import com.example.ui.theme.ArcadeNeonCyan
import com.example.ui.theme.ArcadeNeonGreen
import com.example.ui.theme.ArcadePurple
import com.example.ui.theme.ArcadeText
import com.example.ui.theme.ArcadeTextDim
import com.example.ui.theme.ArcadeTextDisabled
import com.example.ui.theme.ArcadeTextMuted

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
    onResetProgressClick: (() -> Unit)? = null,
    isRefreshing: Boolean = false,
    onRefreshClick: () -> Unit = {},
    refreshMessage: String? = null,
    onClearRefreshMessage: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showDisclaimerDialog by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            title = {
                Text(
                    text = "Reset Player Progress?",
                    fontWeight = FontWeight.Black,
                    fontSize = 17.sp,
                    color = ArcadeText
                )
            },
            text = {
                Text(
                    text = "This will clear all completed levels and restore coins to 150. Use this to remove any test progress created during Admin testing. Are you sure?",
                    fontSize = 13.sp,
                    color = ArcadeTextDim,
                    lineHeight = 18.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResetConfirmDialog = false
                        onResetProgressClick?.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Reset Progress", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showResetConfirmDialog = false },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, ArcadeBorder)
                ) {
                    Text("Cancel", color = ArcadeText, fontSize = 12.sp)
                }
            },
            shape = RoundedCornerShape(20.dp),
            containerColor = ArcadeCard
        )
    }

    LaunchedEffect(refreshMessage) {
        refreshMessage?.let {
            snackbarHostState.showSnackbar(it)
            onClearRefreshMessage?.invoke()
        }
    }

    if (showDisclaimerDialog) {
        LegalDisclaimerDialog(
            isAgreementMode = false,
            onDismiss = { showDisclaimerDialog = false }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 2.dp)
                    ) {
                        // Squircle app badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ArcadeFlame,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        Brush.linearGradient(listOf(ArcadeFlame, ArcadeGold))
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "L",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 18.sp,
                                    color = ArcadeCanvas
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "LOGO QUIZ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = ArcadeText,
                                letterSpacing = (-0.5).sp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = ArcadeCardElevated,
                                    border = BorderStroke(0.5.dp, ArcadeBorder),
                                    modifier = Modifier.size(14.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = "🔥", fontSize = 8.sp)
                                    }
                                }
                                Text(
                                    text = "7 DAY STREAK",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp,
                                    color = ArcadeFlameSecondary
                                )
                            }
                        }
                    }
                },
                actions = {
                    // Terms / Legal lore icon
                    IconButton(
                        onClick = { showDisclaimerDialog = true },
                        modifier = Modifier.testTag("help_disclaimer_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Description,
                            contentDescription = "Legal & Trademark Disclaimer",
                            tint = ArcadeTextDim,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Sync jsDelivr CDN logos
                    IconButton(
                        onClick = onRefreshClick,
                        enabled = !isRefreshing,
                        modifier = Modifier.testTag("sync_cdn_main_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = "Sync jsDelivr CDN logos",
                            tint = ArcadeNeonCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Mode Switcher Pill (Admin vs User)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isAdminMode) Color(0xFF0E2A1A) else ArcadeCardSecondary,
                        border = BorderStroke(1.dp, if (isAdminMode) ArcadeNeonGreen.copy(alpha = 0.5f) else ArcadeBorder),
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
                                tint = if (isAdminMode) ArcadeNeonGreen else ArcadeTextDim,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isAdminMode) "Admin" else "User",
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp,
                                color = if (isAdminMode) ArcadeNeonGreen else ArcadeText
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Icon(
                                imageVector = Icons.Rounded.SwapHoriz,
                                contentDescription = "Switch",
                                tint = ArcadeTextDim,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Coins Pill with real coin count and "+"
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = ArcadeCardElevated,
                        border = BorderStroke(1.dp, ArcadeBorder),
                        modifier = Modifier
                            .clickable { onEarnCoinsClick() }
                            .testTag("earn_coins_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🪙", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${userProfile?.coins ?: 150}",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.5.sp,
                                color = ArcadeText
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Surface(
                                shape = CircleShape,
                                color = Color.White,
                                modifier = Modifier.size(16.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "+",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 11.sp,
                                        color = ArcadeCanvas
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ArcadeBg)
            )
        },
        containerColor = ArcadeBg
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Mode Status Banner
            if (isAdminMode) {
                item {
                    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                    val pulseAlpha by infiniteTransition.animateFloat(
                        initialValue = 0.4f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(800, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "adminDotPulse"
                    )

                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = Color(0xFF0E2A1A),
                        border = BorderStroke(1.dp, ArcadeNeonGreen.copy(alpha = 0.35f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = ArcadeNeonGreen,
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = "✓", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 11.sp)
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
                                    text = "ADMIN MODE ACTIVE",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 11.sp,
                                    letterSpacing = 1.sp,
                                    color = ArcadeNeonGreen
                                )

                                Text(
                                    text = " • All logos unlocked",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ArcadeTextDim
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(ArcadeNeonGreen.copy(alpha = pulseAlpha))
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0x33DC2626),
                                    border = BorderStroke(0.5.dp, Color(0xFFDC2626).copy(alpha = 0.6f)),
                                    modifier = Modifier
                                        .clickable { showResetConfirmDialog = true }
                                        .testTag("admin_reset_progress_pill")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.DeleteOutline,
                                            contentDescription = null,
                                            tint = Color(0xFFF87171),
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Reset Player Progress",
                                            fontSize = 10.5.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFF87171)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                item {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = ArcadeCardElevated,
                        border = BorderStroke(1.dp, ArcadeBorderSubtle),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "●", color = ArcadeNeonGreen, fontSize = 10.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CAMPAIGN MODE",
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = ArcadeNeonGreen
                            )
                            Text(
                                text = " • Sequential progression • Solve to unlock",
                                fontSize = 11.sp,
                                color = ArcadeTextDim
                            )
                        }
                    }
                }
            }

            // Watch Ad Banner (+50 Coins)
            item {
                WatchAdHeroBanner(onEarnCoinsClick = onEarnCoinsClick)
            }

            // Quick Play Hero Card
            item {
                QuickPlayHeroCard(onPlayClick = onQuickPlayClick)
            }

            // Campaign Overview Stats Card (Dynamic and Real)
            val totalLogos = packSummaries.sumOf { it.totalLevels }
            val totalSolved = packSummaries.sumOf { it.completedLevels }
            val totalStars = packSummaries.sumOf { it.totalStars }
            val overallPercentage = if (totalLogos > 0) (totalSolved * 100) / totalLogos else 0

            item {
                Surface(
                    shape = RoundedCornerShape(22.dp),
                    color = ArcadeCard,
                    border = BorderStroke(1.dp, ArcadeBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "CAMPAIGN OVERVIEW",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.2.sp,
                                    color = ArcadeTextMuted
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "$totalLogos Authentic Logos",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ArcadeText
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = ArcadeNeonGreen.copy(alpha = 0.15f),
                                border = BorderStroke(1.dp, ArcadeNeonGreen.copy(alpha = 0.35f))
                            ) {
                                Text(
                                    text = "$overallPercentage% Solved",
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ArcadeNeonGreen,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Progress bar
                        LinearProgressIndicator(
                            progress = { if (totalLogos > 0) totalSolved.toFloat() / totalLogos else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = ArcadeNeonGreen,
                            trackColor = ArcadeCardSecondary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Real Stats Breakdown Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = null,
                                    tint = ArcadeNeonGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "$totalSolved / $totalLogos Solved",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ArcadeText
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Rounded.Star,
                                    contentDescription = null,
                                    tint = ArcadeGold,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "$totalStars / ${totalLogos * 3} Stars",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ArcadeText
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = ArcadeCardSecondary,
                                border = BorderStroke(0.5.dp, ArcadeBorderSubtle)
                            ) {
                                Text(
                                    text = "${packSummaries.size} Packs",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ArcadeTextDim,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            // GAME PACKS Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GAME PACKS",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black,
                        color = ArcadeText,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "${packSummaries.size} WORLDS",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.2.sp,
                        color = ArcadeTextMuted
                    )
                }
            }

            // 2-Column Grid of Pack Cards
            val chunkedPacks = packSummaries.chunked(2)
            var currentPackIndex = 1
            chunkedPacks.forEach { rowPacks ->
                val startIndex = currentPackIndex
                currentPackIndex += rowPacks.size
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowPacks.forEachIndexed { colIdx, summary ->
                            DarkArcadePackCard(
                                summary = summary,
                                packIndex = startIndex + colIdx,
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
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

/**
 * Watch Ad Hero Banner (+50 Coins) matching the artifact
 */
@Composable
private fun WatchAdHeroBanner(
    onEarnCoinsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = ArcadeFlame,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onEarnCoinsClick() }
            .testTag("earn_coins_hero_card")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(ArcadeFlameDark, ArcadeGoldLight)
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Coin Bubble
                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.2f),
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🪙", fontSize = 22.sp)
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "+50 COINS",
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp,
                            color = Color.Black,
                            letterSpacing = (-0.3).sp
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "WATCH • 12s",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = Color.Black.copy(alpha = 0.7f)
                        )
                    }
                }

                // Play Arrow Button
                Surface(
                    shape = CircleShape,
                    color = Color.Black,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Rounded.PlayArrow,
                            contentDescription = "Watch Ad",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Mixed Quick Play Card matching the artifact
 */
@Composable
private fun QuickPlayHeroCard(
    onPlayClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = ArcadeDarkTeal,
        border = BorderStroke(1.dp, ArcadeNeonCyan.copy(alpha = 0.2f)),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPlayClick() }
            .testTag("quick_play_card")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(ArcadeDarkTeal, ArcadeDarkBlue)
                    )
                )
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Dice squircle
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = ArcadeNeonGreen,
                        modifier = Modifier.size(54.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.linearGradient(listOf(ArcadeNeonGreen, ArcadeNeonCyan))),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🎲", fontSize = 28.sp)
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ArcadeNeonGreen,
                                modifier = Modifier.padding(end = 6.dp)
                            ) {
                                Text(
                                    text = "MIXED",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = "QUICK PLAY",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = Color.White.copy(alpha = 0.5f)
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "10 random logos",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            listOf("🏎️", "🍔", "💻").forEach { emoji ->
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFF1E2A36),
                                    border = BorderStroke(1.dp, Color(0xFF0E3A3F)),
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = emoji, fontSize = 10.sp)
                                    }
                                }
                            }
                            Text(
                                text = "+5 more",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.5f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Play button
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Text(
                            text = "PLAY",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Accent mini progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.Black.copy(alpha = 0.3f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .height(4.dp)
                            .background(Brush.horizontalGradient(listOf(ArcadeNeonGreen, ArcadeNeonCyan)))
                    )
                }
            }
        }
    }
}

/**
 * Pack Card matching the Dark Arcade 2-column layout in the artifact
 */
@Composable
private fun DarkArcadePackCard(
    summary: PackProgressSummary,
    packIndex: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pack = summary.pack
    val gradientColors = pack.gradientColorsHex.map { Color(it) }
    val accentColor = Color(pack.accentColorHex)
    val pct = if (summary.totalLevels > 0) (summary.completedLevels * 100) / summary.totalLevels else 0

    Surface(
        shape = RoundedCornerShape(22.dp),
        color = ArcadeCard,
        border = BorderStroke(1.dp, ArcadeBorder),
        modifier = modifier
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Top row: Icon Squircle + Circular SVG/Canvas Progress Ring
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category icon squircle
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = accentColor,
                        modifier = Modifier.size(46.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.linearGradient(gradientColors)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = pack.emoji,
                                fontSize = 22.sp
                            )
                        }
                    }

                    // Circular Progress Ring
                    Box(
                        modifier = Modifier.size(44.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val strokeWidth = 3.5.dp.toPx()
                            // Background track ring
                            drawArc(
                                color = Color(0xFF222236),
                                startAngle = 0f,
                                sweepAngle = 360f,
                                useCenter = false,
                                style = Stroke(width = strokeWidth)
                            )
                            // Foreground progress arc
                            drawArc(
                                color = accentColor,
                                startAngle = -90f,
                                sweepAngle = (pct / 100f) * 360f,
                                useCenter = false,
                                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                            )
                        }
                        Text(
                            text = "$pct%",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // "PACK 0X"
                Text(
                    text = "PACK %02d".format(packIndex),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.2.sp,
                    color = ArcadeTextMuted
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Category Title
                Text(
                    text = pack.title,
                    fontSize = 14.5.sp,
                    fontWeight = FontWeight.Black,
                    color = ArcadeText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Mini progress bar + count
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(5.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFF222236))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(if (summary.totalLevels > 0) summary.completedLevels.toFloat() / summary.totalLevels else 0f)
                                .height(5.dp)
                                .background(accentColor)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "${summary.completedLevels}/${summary.totalLevels}",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = ArcadeTextDim
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Stars row + Logos badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val starsCount = if (summary.totalLevels > 0) {
                            (summary.completedLevels * 3) / summary.totalLevels
                        } else 0

                        repeat(3) { starIdx ->
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (starIdx < starsCount) ArcadeGold else Color.White.copy(alpha = 0.12f)
                                    )
                            )
                        }
                    }

                    Text(
                        text = "${summary.totalLevels} LOGOS",
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
