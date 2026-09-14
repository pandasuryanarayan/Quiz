package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.TailwindBlue
import com.example.ui.theme.WarmBorder
import com.example.ui.theme.WarmSurface2
import com.example.ui.theme.WarmText

data class BankTile(
    val id: Int,
    val char: Char,
    val isUsed: Boolean = false,
    val isRemovedByHint: Boolean = false
)

@Composable
fun LetterBank(
    tiles: List<BankTile>,
    onTileClick: (Int) -> Unit,
    onShuffle: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val halfSize = (tiles.size + 1) / 2
    val firstRow = tiles.take(halfSize)
    val secondRow = tiles.drop(halfSize)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Controls row: Shuffle & Clear
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier
                    .shadow(1.dp, RoundedCornerShape(12.dp))
                    .clickable { onShuffle() }
                    .testTag("shuffle_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Shuffle,
                        contentDescription = "Shuffle letter bank",
                        tint = TailwindBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Shuffle",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TailwindBlue
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Slate200),
                modifier = Modifier
                    .shadow(1.dp, RoundedCornerShape(12.dp))
                    .clickable { onClearAll() }
                    .testTag("clear_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Backspace,
                        contentDescription = "Clear all slots",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Clear",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFEF4444)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // First row of tiles
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth()
        ) {
            firstRow.forEach { tile ->
                BankTileItem(tile = tile, onClick = { onTileClick(tile.id) })
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Second row of tiles
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth()
        ) {
            secondRow.forEach { tile ->
                BankTileItem(tile = tile, onClick = { onTileClick(tile.id) })
            }
        }
    }
}

@Composable
private fun BankTileItem(
    tile: BankTile,
    onClick: () -> Unit
) {
    val isVisible = !tile.isUsed && !tile.isRemovedByHint

    Box(
        modifier = Modifier.size(width = 44.dp, height = 48.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(150)) + scaleIn(tween(150)),
            exit = fadeOut(tween(150)) + scaleOut(tween(150))
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(elevation = 1.dp, shape = RoundedCornerShape(8.dp))
                    .clickable { onClick() }
                    .testTag("bank_tile_${tile.id}"),
                shape = RoundedCornerShape(8.dp),
                color = WarmSurface2,
                border = BorderStroke(1.dp, WarmBorder)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tile.char.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmText
                    )
                }
            }
        }

        if (tile.isRemovedByHint) {
            // Disabled crossed-out placeholder
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF1F5F9),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = tile.char.toString(),
                        fontSize = 18.sp,
                        color = Slate400,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
