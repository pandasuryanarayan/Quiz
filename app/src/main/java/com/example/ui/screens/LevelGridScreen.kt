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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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

    Scaffold(
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

            // Level Tiles (1 to 10)
            items(packLevels) { level ->
                val progress = allProgress.find { it.id == level.id }
                val isUnlocked = progress?.isUnlocked == true || level.levelNumber <= 5
                val isCompleted = progress?.isCompleted == true
                val stars = progress?.stars ?: 0

                LevelGridTile(
                    level = level,
                    isUnlocked = isUnlocked,
                    isCompleted = isCompleted,
                    stars = stars,
                    onClick = { onLevelClick(level.id) },
                    modifier = Modifier.testTag("level_item_${level.id}")
                )
            }
        }
    }
}

@Composable
private fun LevelGridTile(
    level: QuizLevel,
    isUnlocked: Boolean,
    isCompleted: Boolean,
    stars: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = when {
            isCompleted -> Color(0xFFF0FDF4)
            isUnlocked -> Color.White
            else -> Color(0xFFF8FAFC)
        },
        border = BorderStroke(
            width = if (isCompleted || isUnlocked) 1.5.dp else 1.dp,
            color = when {
                isCompleted -> EmeraldSuccess
                isUnlocked -> TailwindBlue.copy(alpha = 0.5f)
                else -> Slate200
            }
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(115.dp)
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
                        color = if (isUnlocked) Slate500 else Slate400,
                        letterSpacing = 0.5.sp
                    )

                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "Completed",
                            tint = EmeraldSuccess,
                            modifier = Modifier.size(18.dp)
                        )
                    } else if (!isUnlocked) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFEFF6FF),
                            modifier = Modifier.size(22.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Rounded.Lock,
                                    contentDescription = "Locked",
                                    tint = TailwindBlue,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }
                    }
                }

                // Center Content: Stars or Play Prompt or Ad Unlock Tag
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
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
                    } else if (isUnlocked) {
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
                    } else {
                        // Ad Gated Badge
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
                }

                // Bottom Subtitle (Letters count or status)
                Text(
                    text = "${level.answer.length} Letters",
                    fontSize = 11.sp,
                    color = if (isUnlocked) Slate500 else Slate400,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
