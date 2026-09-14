package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CoralRed
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.TailwindBlue
import com.example.ui.theme.WarmBorderBright
import com.example.ui.theme.WarmSurface2
import com.example.ui.theme.WarmTextDim
import com.example.ui.theme.WireAmber
import com.example.ui.theme.WireRose
import com.example.ui.theme.WireSage
import com.example.ui.theme.WireTeal
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
    val slotSize: Dp = when {
        slots.size <= 5 -> 50.dp
        slots.size <= 7 -> 42.dp
        slots.size <= 9 -> 36.dp
        else -> 32.dp
    }
    val fontSize = when {
        slots.size <= 5 -> 22.sp
        slots.size <= 7 -> 19.sp
        slots.size <= 9 -> 16.sp
        else -> 14.sp
    }

    Row(
        modifier = modifier
            .offset { IntOffset(x = shakeOffset.value.roundToInt(), y = 0) }
            .padding(horizontal = 8.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        slots.forEachIndexed { index, slot ->
            LetterSlotTile(
                slot = slot,
                slotState = slotState,
                size = slotSize,
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
    size: Dp,
    fontSize: androidx.compose.ui.unit.TextUnit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isFilled = slot.char != null

    val borderColor by animateColorAsState(
        targetValue = when {
            slotState == SlotState.CORRECT -> WireSage
            slotState == SlotState.ERROR -> WireRose
            slot.isRevealedByHint -> WireAmber
            isFilled -> WireTeal
            else -> WarmBorderBright
        },
        animationSpec = tween(durationMillis = 200),
        label = "slotBorderColor"
    )

    val bgColor by animateColorAsState(
        targetValue = when {
            slotState == SlotState.CORRECT -> WireSage
            slotState == SlotState.ERROR -> WireRose
            slot.isRevealedByHint -> WireAmber
            isFilled -> WireTeal
            else -> WarmSurface2
        },
        animationSpec = tween(durationMillis = 200),
        label = "slotBgColor"
    )

    val textColor = when {
        slotState == SlotState.CORRECT -> Color.White
        slotState == SlotState.ERROR -> Color.White
        slot.isRevealedByHint -> Color.White
        isFilled -> Color.White
        else -> WarmTextDim
    }

    Surface(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = if (isFilled) 2.dp else 0.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = isFilled && !slot.isRevealedByHint) { onClick() },
        shape = RoundedCornerShape(8.dp),
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
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            } else {
                // Subtle underline marker in blank slot
                Box(
                    modifier = Modifier
                        .size(width = 12.dp, height = 2.dp)
                        .align(Alignment.BottomCenter)
                        .offset(y = (-6).dp)
                        .scale(1f)
                ) {
                    Surface(
                        color = Slate400.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(1.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {}
                }
            }
        }
    }
}
