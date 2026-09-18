package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ArcadeBorder
import com.example.ui.theme.ArcadeCard
import com.example.ui.theme.ArcadeGold
import com.example.ui.theme.ArcadeNeonGreen
import com.example.ui.theme.ArcadeText
import com.example.ui.theme.ArcadeTextDisabled
import com.example.ui.theme.CoralRed
import kotlin.math.roundToInt

enum class SlotState {
    DEFAULT,
    CORRECT,
    ERROR
}

data class SlotItem(
    val index: Int,
    val char: Char?,
    val bankTileId: Int?,
    val isRevealedByHint: Boolean = false
)

@Composable
fun LetterSlotsRow(
    slots: List<SlotItem>,
    slotState: SlotState,
    shakeTrigger: Int,
    onSlotClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(shakeTrigger) {
        if (shakeTrigger > 0) {
            shakeOffset.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 400
                    0f at 0
                    (-14f) at 50
                    14f at 100
                    (-10f) at 150
                    10f at 200
                    (-6f) at 250
                    6f at 300
                    0f at 400
                }
            )
        }
    }

    // Adaptive sizing depending on answer length
    val slotWidth: Dp = when {
        slots.size <= 5 -> 50.dp
        slots.size <= 7 -> 44.dp
        slots.size <= 9 -> 36.dp
        else -> 30.dp
    }
    val slotHeight: Dp = when {
        slots.size <= 5 -> 60.dp
        slots.size <= 7 -> 54.dp
        slots.size <= 9 -> 46.dp
        else -> 40.dp
    }
    val fontSize = when {
        slots.size <= 5 -> 22.sp
        slots.size <= 7 -> 19.sp
        slots.size <= 9 -> 16.sp
        else -> 13.sp
    }

    Row(
        modifier = modifier
            .offset { IntOffset(x = shakeOffset.value.roundToInt(), y = 0) }
            .padding(horizontal = 4.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        slots.forEachIndexed { index, slot ->
            LetterSlotTile(
                slot = slot,
                slotState = slotState,
                width = slotWidth,
                height = slotHeight,
                fontSize = fontSize,
                onClick = { onSlotClick(index) },
                modifier = Modifier.testTag("letter_slot_$index")
            )
        }
    }
}

@Composable
private fun LetterSlotTile(
    slot: SlotItem,
    slotState: SlotState,
    width: Dp,
    height: Dp,
    fontSize: androidx.compose.ui.unit.TextUnit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFilled = slot.char != null

    val borderColor by animateColorAsState(
        targetValue = when {
            slotState == SlotState.CORRECT -> ArcadeNeonGreen
            slotState == SlotState.ERROR -> CoralRed
            slot.isRevealedByHint -> ArcadeGold
            isFilled -> Color.White
            else -> ArcadeBorder
        },
        animationSpec = tween(durationMillis = 200),
        label = "slotBorderColor"
    )

    val bgColor by animateColorAsState(
        targetValue = when {
            slotState == SlotState.CORRECT -> ArcadeNeonGreen
            slotState == SlotState.ERROR -> CoralRed
            slot.isRevealedByHint -> ArcadeGold
            isFilled -> Color.White
            else -> ArcadeCard
        },
        animationSpec = tween(durationMillis = 200),
        label = "slotBgColor"
    )

    val textColor = when {
        slotState == SlotState.CORRECT -> Color.Black
        slotState == SlotState.ERROR -> Color.White
        slot.isRevealedByHint -> Color.Black
        isFilled -> Color.Black
        else -> ArcadeTextDisabled
    }

    Surface(
        modifier = modifier
            .size(width = width, height = height)
            .shadow(
                elevation = if (isFilled) 6.dp else 0.dp,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(enabled = isFilled && !slot.isRevealedByHint) { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = bgColor,
        border = BorderStroke(if (isFilled) 2.dp else 1.5.dp, borderColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (slot.char != null) {
                Text(
                    text = slot.char.toString(),
                    fontSize = fontSize,
                    fontWeight = FontWeight.Black,
                    color = textColor
                )
            } else {
                Text(
                    text = "•",
                    fontSize = 18.sp,
                    color = ArcadeTextDisabled
                )
            }
        }
    }
}
