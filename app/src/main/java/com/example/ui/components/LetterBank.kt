package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeBorderSubtle
import com.example.ui.theme.ArcadeCard
import com.example.ui.theme.ArcadeCardElevated
import com.example.ui.theme.ArcadeCardSecondary
import com.example.ui.theme.ArcadeKeyBg
import com.example.ui.theme.ArcadeKeyBorder
import com.example.ui.theme.ArcadeKeyDisabled
import com.example.ui.theme.ArcadeKeyShadow
import com.example.ui.theme.ArcadeText
import com.example.ui.theme.ArcadeTextDim
import com.example.ui.theme.ArcadeTextDisabled
import com.example.ui.theme.ArcadeTextMuted

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
        // Controls row: Clear & Shuffle pills
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = ArcadeCardElevated,
                border = BorderStroke(1.dp, ArcadeBorder),
                modifier = Modifier
                    .clickable { onClearAll() }
                    .testTag("clear_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Clear",
                        tint = ArcadeTextDim,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "CLEAR",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        color = ArcadeTextDim
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Surface(
                shape = RoundedCornerShape(18.dp),
                color = ArcadeCardElevated,
                border = BorderStroke(1.dp, ArcadeBorder),
                modifier = Modifier
                    .clickable { onShuffle() }
                    .testTag("shuffle_button")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Shuffle,
                        contentDescription = "Shuffle",
                        tint = ArcadeTextDim,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "SHUFFLE",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        color = ArcadeTextDim
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Keyboard Container
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = ArcadeCardSecondary,
            border = BorderStroke(1.dp, ArcadeBorderSubtle),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // First row of letters
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
                ) {
                    firstRow.forEach { tile ->
                        BankKeyTile(
                            tile = tile,
                            onClick = { onTileClick(tile.id) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("bank_tile_${tile.id}")
                        )
                    }
                }

                // Second row of letters
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
                ) {
                    secondRow.forEach { tile ->
                        BankKeyTile(
                            tile = tile,
                            onClick = { onTileClick(tile.id) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("bank_tile_${tile.id}")
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BankKeyTile(
    tile: BankTile,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = !tile.isRemovedByHint,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
        modifier = modifier
    ) {
        val isEnabled = !tile.isUsed && !tile.isRemovedByHint

        Surface(
            modifier = Modifier
                .height(52.dp)
                .clickable(enabled = isEnabled) { onClick() },
            shape = RoundedCornerShape(14.dp),
            color = if (isEnabled) ArcadeKeyBg else ArcadeKeyDisabled,
            border = BorderStroke(
                width = 1.dp,
                color = if (isEnabled) ArcadeBorderSubtle else ArcadeKeyBorder
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tile.char.toString(),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Black,
                    color = if (isEnabled) ArcadeText else ArcadeTextDisabled
                )
            }
        }
    }

    if (tile.isRemovedByHint) {
        Spacer(modifier = modifier.height(52.dp))
    }
}
