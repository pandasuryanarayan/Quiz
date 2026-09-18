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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Star
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LevelProgressEntity
import com.example.data.PackCategory
import com.example.data.QuizLevel
import com.example.data.QuizPackData
import com.example.ui.theme.ArcadeBg
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeBorderBright
import com.example.ui.theme.ArcadeBorderSubtle
import com.example.ui.theme.ArcadeCanvas
import com.example.ui.theme.ArcadeCard
import com.example.ui.theme.ArcadeCardElevated
import com.example.ui.theme.ArcadeCardSecondary
import com.example.ui.theme.ArcadeFlame
import com.example.ui.theme.ArcadeFlameSecondary
import com.example.ui.theme.ArcadeGold
import com.example.ui.theme.ArcadeNeonCyan
import com.example.ui.theme.ArcadeNeonGreen
import com.example.ui.theme.ArcadeText
import com.example.ui.theme.ArcadeTextDim
import com.example.ui.theme.ArcadeTextDisabled
import com.example.ui.theme.ArcadeTextMuted

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
    val gradientColors = pack.gradientColorsHex.map { Color(it) }
    val accentColor = Color(pack.accentColorHex)

    val completedCount = packLevels.count { level ->
        allProgress.find { it.id == level.id }?.isCompleted == true
    }
    val percentage = if (packLevels.isNotEmpty()) (completedCount * 100 / packLevels.size) else 0
    val totalXp = completedCount * 100

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
                navigationIcon = {
                    Surface(
                        shape = CircleShape,
                        color = ArcadeCardElevated,
                        border = BorderStroke(1.dp, ArcadeBorder),
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .size(38.dp)
                            .clickable { onBack() }
                            .testTag("back_button")
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back",
                                tint = ArcadeText,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = accentColor,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.linearGradient(gradientColors)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = pack.emoji, fontSize = 16.sp)
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = pack.title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = ArcadeText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ArcadeCardElevated,
                            border = BorderStroke(0.5.dp, ArcadeBorder),
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = "${packLevels.size} LOGOS",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 1.sp,
                                color = ArcadeTextDim,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                            )
                        }
                    }
                },
                actions = {
                    // Sync CDN Button
                    IconButton(
                        onClick = onRefreshClick,
                        enabled = !isRefreshing,
                        modifier = Modifier.testTag("sync_cdn_pack_button")
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = "Sync logos",
                            tint = ArcadeNeonCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Coins Pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = ArcadeCardElevated,
                        border = BorderStroke(1.dp, ArcadeBorder),
                        modifier = Modifier
                            .padding(end = 12.dp)
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
                                text = "$coins",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                color = ArcadeText
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "+",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }
                    }
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
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Pack Progress Hero Banner
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Level badge
                                Surface(
                                    shape = CircleShape,
                                    color = ArcadeFlame,
                                    modifier = Modifier.size(38.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                Brush.linearGradient(
                                                    listOf(ArcadeFlame, ArcadeGold)
                                                )
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "$completedCount",
                                            fontWeight = FontWeight.Black,
                                            fontSize = 15.sp,
                                            color = ArcadeCanvas
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column {
                                    Text(
                                        text = "Level $completedCount Explorer",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = ArcadeText
                                    )
                                    Text(
                                        text = "$completedCount/${packLevels.size} conquered",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = ArcadeTextDim
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "$totalXp XP",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = ArcadeText
                                )
                                Text(
                                    text = "NEXT LVL ${(completedCount + 1) * 100}",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp,
                                    color = ArcadeFlameSecondary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Glowing progress bar
                        LinearProgressIndicator(
                            progress = { if (packLevels.isNotEmpty()) completedCount.toFloat() / packLevels.size else 0f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(7.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = ArcadeFlame,
                            trackColor = Color(0xFF0F0F18)
                        )
                    }
                }
            }

            // Levels Vertical Journey Timeline Header
            item {
                Text(
                    text = "CAMPAIGN PATH",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.4.sp,
                    color = ArcadeTextMuted,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }

            // Levels Vertical Journey
            itemsIndexed(packLevels) { index, level ->
                val progress = allProgress.find { it.id == level.id }
                val isCompleted = progress?.isCompleted == true
                val isUnlocked = isAdminMode || (progress?.isUnlocked == true) || index == 0 || (index > 0 && allProgress.find { it.id == packLevels[index - 1].id }?.isCompleted == true)
                val stars = progress?.stars ?: 0
                val isNextActive = isUnlocked && !isCompleted && (index == 0 || allProgress.find { it.id == packLevels[index - 1].id }?.isCompleted == true)

                ArcadeLevelTimelineRow(
                    level = level,
                    levelNumber = index + 1,
                    isCompleted = isCompleted,
                    isUnlocked = isUnlocked,
                    isNextActive = isNextActive,
                    stars = stars,
                    onClick = { onLevelClick(level.id) },
                    modifier = Modifier.testTag("level_item_${level.id}")
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

/**
 * Vertical Journey Row with node bubble and card
 */
@Composable
private fun ArcadeLevelTimelineRow(
    level: QuizLevel,
    levelNumber: Int,
    isCompleted: Boolean,
    isUnlocked: Boolean,
    isNextActive: Boolean,
    stars: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = isUnlocked) { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Timeline Node Bubble
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = when {
                isCompleted -> ArcadeCard
                isNextActive -> Color.White
                isUnlocked -> ArcadeCardElevated
                else -> ArcadeCardSecondary
            },
            border = BorderStroke(
                width = if (isNextActive) 2.dp else 1.5.dp,
                color = when {
                    isCompleted -> ArcadeNeonGreen.copy(alpha = 0.5f)
                    isNextActive -> Color.White
                    isUnlocked -> ArcadeBorderBright
                    else -> ArcadeBorderSubtle
                }
            ),
            modifier = Modifier.size(52.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                when {
                    isCompleted -> {
                        Surface(
                            shape = CircleShape,
                            color = ArcadeNeonGreen,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = "Solved",
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                    isNextActive -> {
                        Text(
                            text = "$levelNumber",
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp,
                            color = Color.Black
                        )
                    }
                    isUnlocked -> {
                        Text(
                            text = "$levelNumber",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = ArcadeText
                        )
                    }
                    else -> {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = "Locked",
                            tint = ArcadeTextDisabled,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Level Details Card
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = when {
                isNextActive -> Color.White
                isUnlocked -> ArcadeCard
                else -> ArcadeCardSecondary
            },
            border = BorderStroke(
                width = 1.dp,
                color = if (isNextActive) Color.White else ArcadeBorder
            ),
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Logo $levelNumber",
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isNextActive) Color.Black else ArcadeText
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isNextActive) Color.Black.copy(alpha = 0.08f) else Color.White.copy(alpha = 0.06f),
                            border = BorderStroke(0.5.dp, if (isNextActive) Color.Black.copy(alpha = 0.15f) else ArcadeBorderSubtle)
                        ) {
                            Text(
                                text = "${level.answer.length} LETTERS",
                                fontFamily = FontFamily.Monospace,
                                fontSize = 8.5.sp,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.8.sp,
                                color = if (isNextActive) Color.Black.copy(alpha = 0.7f) else ArcadeTextDim,
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = when {
                                isCompleted -> "Solved • 300 XP"
                                isNextActive -> "Current • Tap to Play"
                                isUnlocked -> "Unlocked"
                                else -> "Locked"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isNextActive) Color.Black.copy(alpha = 0.6f) else ArcadeTextMuted
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    // Star Rating
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(3) { starIdx ->
                            Icon(
                                imageVector = Icons.Rounded.Star,
                                contentDescription = null,
                                tint = when {
                                    starIdx < stars -> ArcadeGold
                                    isNextActive -> Color.Black.copy(alpha = 0.15f)
                                    else -> Color.White.copy(alpha = 0.12f)
                                },
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }
                }

                // Action arrow
                Surface(
                    shape = CircleShape,
                    color = if (isNextActive) Color.Black else Color.White.copy(alpha = 0.08f),
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronRight,
                            contentDescription = "Open",
                            tint = if (isNextActive) Color.White else ArcadeTextDim,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
