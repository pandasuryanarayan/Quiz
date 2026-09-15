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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LevelProgressEntity
import com.example.data.PackCategory
import com.example.data.QuizLevel
import com.example.data.QuizPackData
import com.example.ui.components.CategoryHeroImage
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelGridScreen(
    packId: String,
    allProgress: List<LevelProgressEntity>,
    coins: Int,
    isAdminMode: Boolean = false,
    isRefreshing: Boolean = false,
    refreshMessage: String? = null,
    onRefreshClick: () -> Unit = {},
    onClearRefreshMessage: () -> Unit = {},
    onLevelClick: (String) -> Unit,
    onBack: () -> Unit,
    onEarnCoinsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pack = PackCategory.entries.find { it.id == packId } ?: PackCategory.AUTOMOTIVE
    val packLevels = QuizPackData.getLevelsForPack(packId)
    val packColor = Color(pack.primaryColorHex)
    val packDimColor = Color(pack.dimColorHex)

    val completedCount = packLevels.count { level ->
        allProgress.find { it.id == level.id }?.isCompleted == true
    }
    val percentage = if (packLevels.isNotEmpty()) (completedCount * 100 / packLevels.size) else 0

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(refreshMessage) {
        refreshMessage?.let {
            snackbarHostState.showSnackbar(it)
            onClearRefreshMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.clickable { onBack() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = WarmTextDim,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Back",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = WarmTextDim
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onRefreshClick,
                        enabled = !isRefreshing,
                        modifier = Modifier.testTag("sync_cdn_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = "Sync jsDelivr CDN logos",
                            tint = WireTeal,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Coins Pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFEF3C7),
                        border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                        modifier = Modifier
                            .clickable { onEarnCoinsClick() }
                            .testTag("grid_earn_coins_button")
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
                                text = "$coins",
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmBg)
            )
        },
        containerColor = WarmBg
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Admin Mode Active Banner
            if (isAdminMode) {
                item {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFEFF6FF),
                        border = BorderStroke(1.dp, Color(0xFF93C5FD)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AdminPanelSettings,
                                contentDescription = null,
                                tint = Color(0xFF1D4ED8),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Admin Mode • All logos unlocked for testing",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1D4ED8)
                            )
                        }
                    }
                }
            }

            // Category Hero Banner
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = WarmSurface,
                    border = BorderStroke(1.dp, WarmBorderBright),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CategoryHeroImage(
                            pack = pack,
                            height = 110.dp,
                            totalLogos = packLevels.size,
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp)
                        ) {
                            Text(
                                text = pack.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = WarmText,
                                letterSpacing = (-0.3).sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = pack.subtitle,
                                fontSize = 12.sp,
                                color = WarmTextDim
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LinearProgressIndicator(
                                progress = { if (packLevels.isNotEmpty()) completedCount.toFloat() / packLevels.size else 0f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(2.5.dp)),
                                color = packColor,
                                trackColor = WarmBorder
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$completedCount / ${packLevels.size} identified",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = WarmTextDim
                                )
                                Text(
                                    text = "$percentage%",
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = packColor
                                )
                            }
                        }
                    }
                }
            }

            // Section Heading: LOGOS
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 2.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LOGOS",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        color = WarmTextDim
                    )
                    Text(
                        text = "$completedCount of ${packLevels.size} Solved",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        color = WarmTextDim
                    )
                }
            }

            // Logo Rows (Wireframe level-list style)
            if (packLevels.isEmpty()) {
                item {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = WarmSurface,
                        border = BorderStroke(1.dp, WarmBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "📂",
                                fontSize = 36.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "No logos found on CDN yet",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = WarmText
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "To add logos to this category, upload webp or png files into the '${pack.folderName}' folder to be served by jsDelivr CDN, then tap Sync below.",
                                fontSize = 12.sp,
                                color = WarmTextDim,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                lineHeight = 17.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onRefreshClick,
                                enabled = !isRefreshing,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = WireTeal,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.testTag("empty_state_sync_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isRefreshing) "Syncing..." else "Sync from CDN",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            } else {
                items(packLevels) { level ->
                    val progress = allProgress.find { it.id == level.id }
                    val isCompleted = progress?.isCompleted == true
                    val lockStatus = QuizPackData.getLevelLockStatus(level, allProgress, isAdminMode)

                    LogoWireRow(
                        level = level,
                        isCompleted = isCompleted,
                        isUnlocked = lockStatus.isUnlocked,
                        isAdGated = lockStatus.isAdGated,
                        stars = progress?.stars ?: 0,
                        packColor = packColor,
                        onClick = { onLevelClick(level.id) },
                        modifier = Modifier.testTag("logo_row_${level.id}")
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun LogoWireRow(
    level: QuizLevel,
    isCompleted: Boolean,
    isUnlocked: Boolean,
    isAdGated: Boolean,
    stars: Int,
    packColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rowBg = when {
        isCompleted -> WireSageDim
        isUnlocked -> WarmSurface
        else -> WarmSurface.copy(alpha = 0.55f)
    }

    val rowBorder = when {
        isCompleted -> WireSage
        isUnlocked -> WarmBorder
        else -> WarmBorder.copy(alpha = 0.6f)
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = rowBg,
        border = BorderStroke(1.dp, rowBorder),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circle Status Indicator
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isCompleted -> WireSage
                            isUnlocked -> WarmSurface2
                            else -> WarmSurface2.copy(alpha = 0.6f)
                        }
                    )
                    .then(
                        if (!isCompleted && isUnlocked) {
                            Modifier.background(WarmSurface2)
                        } else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isCompleted -> {
                        Icon(
                            imageVector = Icons.Rounded.Check,
                            contentDescription = "Solved",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    isUnlocked -> {
                        Text(
                            text = "${level.levelNumber}",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = WireAmber
                        )
                    }
                    else -> {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = "Locked",
                            tint = WarmTextDim,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Logo Name & Subtitle
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Logo ${level.levelNumber}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isUnlocked || isCompleted) WarmText else WarmTextDim
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = when {
                        isCompleted -> "${level.answer.length} letters • Solved"
                        isUnlocked -> "${level.answer.length} letters"
                        isAdGated -> "Watch Ad to unlock"
                        else -> "Complete Logo ${level.levelNumber - 1} to unlock"
                    },
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = when {
                        isCompleted -> WireSage
                        isUnlocked -> WarmTextDim
                        isAdGated -> WireAmber
                        else -> WarmTextDim.copy(alpha = 0.7f)
                    }
                )
            }

            // Right side stars or lock indicator
            if (isCompleted) {
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    val filledStars = if (stars > 0) stars else 3
                    repeat(filledStars) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = WireAmber,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            } else if (isUnlocked) {
                Text(
                    text = "Play ›",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = WireTeal
                )
            } else {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isAdGated) WireAmber.copy(alpha = 0.12f) else Color.Transparent
                ) {
                    Text(
                        text = if (isAdGated) "Ad Unlock" else "Locked",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isAdGated) WireAmber else WarmTextDim,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
