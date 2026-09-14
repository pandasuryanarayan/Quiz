package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.data.PackCategory
import com.example.data.QuizPackData

@Composable
fun CategoryHeroImage(
    pack: PackCategory,
    modifier: Modifier = Modifier,
    height: Dp = 92.dp,
    packIndex: Int? = null,
    totalLogos: Int = 10,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp)
) {
    val coverUrl = QuizPackData.buildCdnUrl(pack.folderName, "cover.webp")
    val gradientColors = pack.gradientColorsHex.map { Color(it) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(shape)
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(coverUrl)
                .crossfade(true)
                .build(),
            contentDescription = "${pack.title} category cover",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                PremiumCategoryFallbackIllustration(
                    pack = pack,
                    gradientColors = gradientColors,
                    packIndex = packIndex,
                    totalLogos = totalLogos
                )
            },
            error = {
                PremiumCategoryFallbackIllustration(
                    pack = pack,
                    gradientColors = gradientColors,
                    packIndex = packIndex,
                    totalLogos = totalLogos
                )
            }
        )
    }
}

@Composable
private fun PremiumCategoryFallbackIllustration(
    pack: PackCategory,
    gradientColors: List<Color>,
    packIndex: Int?,
    totalLogos: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = if (gradientColors.size >= 2) gradientColors else listOf(Color(pack.primaryColorHex), Color(pack.dimColorHex)),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        // Subtle ambient concentric geometric circles
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            // Ambient light burst at top right
            drawCircle(
                color = Color.White.copy(alpha = 0.12f),
                radius = w * 0.45f,
                center = Offset(w * 0.85f, h * 0.2f)
            )
            // Delicate geometric concentric rings
            drawCircle(
                color = Color.White.copy(alpha = 0.15f),
                radius = w * 0.35f,
                center = Offset(w * 0.5f, h * 0.5f),
                style = Stroke(width = 1.5f)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.08f),
                radius = w * 0.6f,
                center = Offset(w * 0.5f, h * 0.5f),
                style = Stroke(width = 1.5f)
            )
        }

        // Centered glassmorphic badge with category emblem emoji
        Surface(
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.22f),
            border = BorderStroke(1.2.dp, Color.White.copy(alpha = 0.45f)),
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(
                    text = pack.emoji,
                    fontSize = 24.sp
                )
            }
        }

        // Top Row: Pack Index on left, Logo Count on right
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (packIndex != null) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.Black.copy(alpha = 0.22f),
                    border = BorderStroke(0.8.dp, Color.White.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = "PACK %02d".format(packIndex),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.95f),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.Black.copy(alpha = 0.22f),
                border = BorderStroke(0.8.dp, Color.White.copy(alpha = 0.3f))
            ) {
                Text(
                    text = "$totalLogos LOGOS",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White.copy(alpha = 0.95f),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}
