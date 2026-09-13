package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Videocam
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LevelProgressEntity
import com.example.data.PackCategory
import com.example.data.QuizLevel
import com.example.data.QuizPackData
import com.example.ui.theme.AmberStar
import com.example.ui.theme.CleanWhite
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900
import com.example.ui.theme.TailwindBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelGridScreen(
    packId: String,
    allProgress: List<LevelProgressEntity>,
    coins: Int,
    isRefreshing: Boolean = false,
    refreshMessage: String? = null,
    onRefreshClick: () -> Unit = {},
    onClearRefreshMessage: () -> Unit = {},
    onLevelClick: (String) -> Unit,
    onBack: () -> Unit,
    onEarnCoinsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pack = PackCategory.entries.find { it.id == packId } ?: PackCategory.BRANDS
    val packLevels = QuizPackData.getLevelsForPack(packId)
    val packColor = Color(pack.primaryColorHex)

    val completedCount = packLevels.count { level ->
        allProgress.find { it.id == level.id }?.isCompleted == true
    }

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
                    Column {
                        Text(
                            text = pack.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Slate900
                        )
                        Text(
                            text = "$completedCount of ${packLevels.size} Solved",
                            fontSize = 11.sp,
                            color = Slate500
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("back_to_packs_button")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back to packs",
                            tint = Slate700
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onRefreshClick,
                        enabled = !isRefreshing,
                        modifier = Modifier.testTag("refresh_category_levels_button")
                    ) {
                        val rotation by animateFloatAsState(
                            targetValue = if (isRefreshing) 360f else 0f,
                            animationSpec = if (isRefreshing) infiniteRepeatable(
                                animation = tween(800, easing = LinearEasing),
                                repeatMode = RepeatMode.Restart
                            ) else tween(300),
                            label = "refresh_rotation"
                        )
                        Icon(
                            imageVector = Icons.Rounded.Refresh,
                            contentDescription = "Refresh category levels",
                            tint = if (isRefreshing) TailwindBlue else Slate700,
                            modifier = Modifier.rotate(rotation)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFEF3C7),
                        border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                        modifier = Modifier
                            .clickable { onEarnCoinsClick() }
                            .testTag("grid_earn_coins_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.MonetizationOn,
                                contentDescription = "Coins",
                                tint = AmberStar,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$coins",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFFB45309)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CleanWhite)
            )
        },
        containerColor = Color(0xFFF8FAFC)
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header information banner
            item(span = { GridItemSpan(2) }) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Slate200),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Pack Progression",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate900
                            )
                            Text(
                                text = "${(completedCount * 100) / packLevels.size}%",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = packColor
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { completedCount.toFloat() / packLevels.size },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = packColor,
                            trackColor = Color(0xFFF1F5F9)
                        )
                    }
                }
            }

            // Only show levels that have been unlocked sequentially
            // Level 1 is always shown. Level N (N > 1) is ONLY shown once Level N-1 has been passed.
            val visibleLevels = QuizPackData.getVisibleLevelsForPack(pack.id, allProgress)

            items(visibleLevels) { level ->
                val progress = allProgress.find { it.id == level.id }
                val lockStatus = QuizPackData.getLevelLockStatus(level, allProgress)
                val stars = progress?.stars ?: 0

                LevelGridTile(
                    level = level,
                    lockStatus = lockStatus,
                    stars = stars,
                    onClick = { onLevelClick(level.id) },
                    modifier = Modifier.testTag("level_item_${level.id}")
                )
            }

            // Progression indicator card if more levels are locked
            if (visibleLevels.size < packLevels.size) {
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFF1F5F9),
                        border = BorderStroke(1.dp, Slate200),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White,
                                modifier = Modifier.size(28.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Rounded.Lock,
                                        contentDescription = null,
                                        tint = Slate500,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Level ${visibleLevels.size + 1} Locked",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Slate700
                                )
                                Text(
                                    text = "Pass Level ${visibleLevels.size} to unlock Level ${visibleLevels.size + 1}",
                                    fontSize = 11.sp,
                                    color = Slate500
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelGridTile(
    level: QuizLevel,
    lockStatus: com.example.data.LevelLockStatus,
    stars: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCompleted = lockStatus.isCompleted
    val isUnlocked = lockStatus.isUnlocked
    val isAdGated = lockStatus.isAdGated
    val isStrictlyLocked = lockStatus.isStrictlyLocked

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = when {
            isCompleted -> Color(0xFFF0FDF4)
            isUnlocked -> Color.White
            isAdGated -> Color(0xFFF8FAFC)
            else -> Color(0xFFF1F5F9)
        },
        border = BorderStroke(
            width = if (isCompleted || isUnlocked) 1.5.dp else 1.dp,
            color = when {
                isCompleted -> EmeraldSuccess
                isUnlocked -> TailwindBlue.copy(alpha = 0.5f)
                isAdGated -> Color(0xFFBFDBFE)
                else -> Slate200
            }
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(118.dp)
            .shadow(if (isUnlocked) 2.dp else 0.dp, RoundedCornerShape(18.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top row: Level tag and status icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "LEVEL ${level.levelNumber}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            isCompleted -> EmeraldSuccess
                            isUnlocked -> Slate700
                            isAdGated -> TailwindBlue
                            else -> Slate400
                        },
                        letterSpacing = 0.5.sp
                    )

                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "Completed",
                            tint = EmeraldSuccess,
                            modifier = Modifier.size(18.dp)
                        )
                    } else if (isAdGated) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFEFF6FF),
                            modifier = Modifier.size(22.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Rounded.Videocam,
                                    contentDescription = "Watch Ad",
                                    tint = TailwindBlue,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }
                    } else if (isStrictlyLocked) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFE2E8F0),
                            modifier = Modifier.size(22.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Rounded.Lock,
                                    contentDescription = "Locked",
                                    tint = Slate500,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }
                }

                // Center Content: Stars or Play Prompt or Ad Unlock Tag or Locked Icon
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        isCompleted -> {
                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                repeat(stars) {
                                    Icon(
                                        imageVector = Icons.Rounded.Star,
                                        contentDescription = null,
                                        tint = AmberStar,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                        isUnlocked -> {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFEFF6FF)
                            ) {
                                Text(
                                    text = "PLAY",
                                    color = TailwindBlue,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp)
                                )
                            }
                        }
                        isAdGated -> {
                            // Ad Gated Badge (Previous level completed, ready to unlock via ad)
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFEFF6FF),
                                border = BorderStroke(1.dp, Color(0xFFBFDBFE))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Videocam,
                                        contentDescription = null,
                                        tint = TailwindBlue,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "WATCH AD",
                                        color = TailwindBlue,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                        else -> {
                            // Strictly Locked (Previous level not completed yet)
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFE2E8F0)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Lock,
                                        contentDescription = null,
                                        tint = Slate500,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "LOCKED",
                                        color = Slate500,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Bottom Subtitle (Letters count or required level)
                Text(
                    text = when {
                        isCompleted -> "${level.answer.length} Letters • Solved"
                        isUnlocked -> "${level.answer.length} Letters"
                        isAdGated -> "Unlock with Ad"
                        else -> "Solve Level ${level.levelNumber - 1}"
                    },
                    fontSize = 10.sp,
                    color = when {
                        isCompleted -> EmeraldSuccess
                        isUnlocked -> Slate500
                        isAdGated -> TailwindBlue
                        else -> Slate400
                    },
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
