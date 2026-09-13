package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun LogoCard(
    logoKey: String,
    imageUrl: String? = null,
    modifier: Modifier = Modifier,
    cardSize: Dp = 200.dp
) {
    Surface(
        modifier = modifier
            .size(cardSize)
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        color = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (!imageUrl.isNullOrBlank()) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Quiz Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                strokeWidth = 3.dp,
                                color = Color(0xFF3B82F6)
                            )
                        }
                    },
                    error = {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawLogoCanvas(logoKey, size.width, size.height)
                        }
                    }
                )
            } else {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawLogoCanvas(logoKey, size.width, size.height)
                }
            }
        }
    }
}

private fun DrawScope.drawLogoCanvas(logoKey: String, w: Float, h: Float) {
    val cx = w / 2f
    val cy = h / 2f

    when (logoKey) {
        "apple" -> drawAppleLogo(cx, cy, w, h)
        "nike" -> drawNikeLogo(cx, cy, w, h)
        "tesla" -> drawTeslaLogo(cx, cy, w, h)
        "spotify" -> drawSpotifyLogo(cx, cy, w, h)
        "target" -> drawTargetLogo(cx, cy, w, h)
        "starbucks" -> drawStarbucksLogo(cx, cy, w, h)
        "twitter" -> drawTwitterLogo(cx, cy, w, h)
        "amazon" -> drawAmazonLogo(cx, cy, w, h)
        "google" -> drawGoogleLogo(cx, cy, w, h)
        "mcdonalds" -> drawMcdonaldsLogo(cx, cy, w, h)
        "zoho" -> drawZohoLogo(cx, cy, w, h)
        "netflix" -> drawNetflixLogo(cx, cy, w, h)
        "disney" -> drawDisneyLogo(cx, cy, w, h)
        "marvel" -> drawMarvelLogo(cx, cy, w, h)
        "youtube" -> drawYoutubeLogo(cx, cy, w, h)
        "pixar" -> drawPixarLogo(cx, cy, w, h)
        "batman" -> drawBatmanLogo(cx, cy, w, h)
        "potter" -> drawPotterLogo(cx, cy, w, h)
        "warner" -> drawWarnerLogo(cx, cy, w, h)
        "hbo" -> drawHboLogo(cx, cy, w, h)
        "twitch" -> drawTwitchLogo(cx, cy, w, h)
        "nintendo" -> drawNintendoLogo(cx, cy, w, h)
        "playstation" -> drawPlaystationLogo(cx, cy, w, h)
        "xbox" -> drawXboxLogo(cx, cy, w, h)
        "steam" -> drawSteamLogo(cx, cy, w, h)
        "discord" -> drawDiscordLogo(cx, cy, w, h)
        "android" -> drawAndroidLogo(cx, cy, w, h)
        "atari" -> drawAtariLogo(cx, cy, w, h)
        "sega" -> drawSegaLogo(cx, cy, w, h)
        "roblox" -> drawRobloxLogo(cx, cy, w, h)
        "linux" -> drawLinuxLogo(cx, cy, w, h)
        "ferrari" -> drawFerrariLogo(cx, cy, w, h)
        "adidas" -> drawAdidasLogo(cx, cy, w, h)
        "puma" -> drawPumaLogo(cx, cy, w, h)
        "olympic" -> drawOlympicLogo(cx, cy, w, h)
        "nba" -> drawNbaLogo(cx, cy, w, h)
        "redbull" -> drawRedbullLogo(cx, cy, w, h)
        "bmw" -> drawBmwLogo(cx, cy, w, h)
        "audi" -> drawAudiLogo(cx, cy, w, h)
        "mercedes" -> drawMercedesLogo(cx, cy, w, h)
        "fifa" -> drawFifaLogo(cx, cy, w, h)
        // FOOD & TREATS
        "pepsi" -> drawPepsiLogo(cx, cy, w, h)
        "burgerking" -> drawBurgerKingLogo(cx, cy, w, h)
        "subway" -> drawSubwayLogo(cx, cy, w, h)
        "pringles" -> drawPringlesLogo(cx, cy, w, h)
        "dominos" -> drawDominosLogo(cx, cy, w, h)
        "oreo" -> drawOreoLogo(cx, cy, w, h)
        "kfc" -> drawKfcLogo(cx, cy, w, h)
        "nutella" -> drawNutellaLogo(cx, cy, w, h)
        "fanta" -> drawFantaLogo(cx, cy, w, h)
        "tacobell" -> drawTacobellLogo(cx, cy, w, h)
        // WORLD WONDERS
        "eiffel" -> drawEiffelLogo(cx, cy, w, h)
        "pyramids" -> drawPyramidsLogo(cx, cy, w, h)
        "liberty" -> drawLibertyLogo(cx, cy, w, h)
        "colosseum" -> drawColosseumLogo(cx, cy, w, h)
        "tajmahal" -> drawTajMahalLogo(cx, cy, w, h)
        "bigben" -> drawBigBenLogo(cx, cy, w, h)
        "fuji" -> drawFujiLogo(cx, cy, w, h)
        "pisa" -> drawPisaLogo(cx, cy, w, h)
        "sphinx" -> drawSphinxLogo(cx, cy, w, h)
        "sydney" -> drawSydneyLogo(cx, cy, w, h)
        else -> drawGenericQuizLogo(cx, cy, w, h)
    }
}

// ----------------- BRANDS -----------------

private fun DrawScope.drawAppleLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val dark = Color(0xFF1E293B)
    val r = w * 0.32f
    // Leaf
    val leafPath = Path().apply {
        moveTo(cx + r * 0.1f, cy - r * 1.3f)
        quadraticBezierTo(cx + r * 0.7f, cy - r * 1.3f, cx + r * 0.6f, cy - r * 0.75f)
        quadraticBezierTo(cx, cy - r * 0.8f, cx + r * 0.1f, cy - r * 1.3f)
        close()
    }
    drawPath(leafPath, dark)

    // Left and right body lobes
    drawCircle(color = dark, radius = r * 0.75f, center = Offset(cx - r * 0.35f, cy + r * 0.1f))
    drawCircle(color = dark, radius = r * 0.75f, center = Offset(cx + r * 0.35f, cy + r * 0.1f))
    drawCircle(color = dark, radius = r * 0.68f, center = Offset(cx - r * 0.28f, cy - r * 0.3f))
    drawCircle(color = dark, radius = r * 0.68f, center = Offset(cx + r * 0.28f, cy - r * 0.3f))

    // Top & bottom stem indentations
    drawCircle(color = Color.White, radius = r * 0.25f, center = Offset(cx, cy - r * 0.7f))
    drawCircle(color = Color.White, radius = r * 0.3f, center = Offset(cx, cy + r * 0.85f))

    // The iconic bite cutout on the right
    drawCircle(color = Color.White, radius = r * 0.46f, center = Offset(cx + r * 0.98f, cy - r * 0.05f))
}

private fun DrawScope.drawNikeLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val swoosh = Path().apply {
        moveTo(cx - w * 0.42f, cy - h * 0.05f)
        quadraticBezierTo(cx - w * 0.15f, cy + h * 0.38f, cx + w * 0.35f, cy - h * 0.35f)
        quadraticBezierTo(cx + w * 0.42f, cy - h * 0.45f, cx + w * 0.38f, cy - h * 0.32f)
        quadraticBezierTo(cx + w * 0.02f, cy + h * 0.18f, cx - w * 0.22f, cy + h * 0.15f)
        quadraticBezierTo(cx - w * 0.38f, cy + h * 0.10f, cx - w * 0.42f, cy - h * 0.05f)
        close()
    }
    drawPath(swoosh, Color(0xFF0F172A))
}

private fun DrawScope.drawTeslaLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFE82127)
    // Arched crown
    val arch = Path().apply {
        moveTo(cx - w * 0.35f, cy - h * 0.28f)
        quadraticBezierTo(cx, cy - h * 0.42f, cx + w * 0.35f, cy - h * 0.28f)
        quadraticBezierTo(cx + w * 0.3f, cy - h * 0.22f, cx + w * 0.28f, cy - h * 0.25f)
        quadraticBezierTo(cx, cy - h * 0.36f, cx - w * 0.28f, cy - h * 0.25f)
        close()
    }
    drawPath(arch, red)

    // Center T body and wings
    val tPath = Path().apply {
        moveTo(cx - w * 0.3f, cy - h * 0.18f)
        quadraticBezierTo(cx, cy - h * 0.28f, cx + w * 0.3f, cy - h * 0.18f)
        lineTo(cx + w * 0.14f, cy - h * 0.1f)
        quadraticBezierTo(cx + w * 0.08f, cy + h * 0.15f, cx, cy + h * 0.38f)
        quadraticBezierTo(cx - w * 0.08f, cy + h * 0.15f, cx - w * 0.14f, cy - h * 0.1f)
        close()
    }
    drawPath(tPath, red)
}

private fun DrawScope.drawSpotifyLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val green = Color(0xFF1DB954)
    val r = w * 0.42f
    drawCircle(green, r, Offset(cx, cy))

    val strokeWidth = w * 0.065f
    val dark = Color(0xFF121212)

    // Three curved soundwaves
    listOf(-0.16f, 0.04f, 0.22f).forEachIndexed { index, yOff ->
        val scale = 1f - index * 0.16f
        val wave = Path().apply {
            moveTo(cx - r * 0.55f * scale, cy + r * yOff + r * 0.12f)
            quadraticBezierTo(cx, cy + r * yOff - r * 0.14f, cx + r * 0.55f * scale, cy + r * yOff + r * 0.04f)
        }
        drawPath(
            wave,
            dark,
            style = Stroke(width = strokeWidth * scale, cap = StrokeCap.Round)
        )
    }
}

private fun DrawScope.drawTargetLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFCC0000)
    val r = w * 0.42f
    drawCircle(red, r, Offset(cx, cy))
    drawCircle(Color.White, r * 0.66f, Offset(cx, cy))
    drawCircle(red, r * 0.33f, Offset(cx, cy))
}

private fun DrawScope.drawStarbucksLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val green = Color(0xFF006241)
    val r = w * 0.42f
    drawCircle(green, r, Offset(cx, cy))
    drawCircle(Color.White, r * 0.88f, Offset(cx, cy), style = Stroke(width = 4.dp.toPx()))

    // Siren face & crown silhouette in white
    val crown = Path().apply {
        moveTo(cx - r * 0.45f, cy - r * 0.35f)
        lineTo(cx - r * 0.25f, cy - r * 0.15f)
        lineTo(cx, cy - r * 0.45f)
        lineTo(cx + r * 0.25f, cy - r * 0.15f)
        lineTo(cx + r * 0.45f, cy - r * 0.35f)
        lineTo(cx + r * 0.35f, cy + r * 0.05f)
        lineTo(cx - r * 0.35f, cy + r * 0.05f)
        close()
    }
    drawPath(crown, Color.White)

    // Star atop crown
    drawCircle(Color.White, r * 0.09f, Offset(cx, cy - r * 0.58f))

    // Waves/Hair curves
    drawCircle(Color.White, r * 0.25f, Offset(cx, cy + r * 0.2f))
    drawCircle(green, r * 0.18f, Offset(cx, cy + r * 0.2f))
}

private fun DrawScope.drawTwitterLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val blue = Color(0xFF1D9BF0)
    val bird = Path().apply {
        moveTo(cx + w * 0.35f, cy - h * 0.25f)
        quadraticBezierTo(cx + w * 0.18f, cy - h * 0.15f, cx + w * 0.12f, cy - h * 0.05f)
        quadraticBezierTo(cx + w * 0.3f, cy - h * 0.05f, cx + w * 0.35f, cy - h * 0.15f)
        quadraticBezierTo(cx + w * 0.12f, cy + h * 0.2f, cx - w * 0.25f, cy + h * 0.18f)
        quadraticBezierTo(cx - w * 0.38f, cy + h * 0.35f, cx - w * 0.4f, cy + h * 0.28f)
        quadraticBezierTo(cx - w * 0.15f, cy + h * 0.05f, cx - w * 0.2f, cy - h * 0.1f)
        quadraticBezierTo(cx - w * 0.05f, cy - h * 0.15f, cx + w * 0.05f, cy - h * 0.35f)
        quadraticBezierTo(cx + w * 0.25f, cy - h * 0.4f, cx + w * 0.35f, cy - h * 0.25f)
        close()
    }
    drawPath(bird, blue)
}

private fun DrawScope.drawAmazonLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val orange = Color(0xFFFF9900)
    val dark = Color(0xFF232F3E)

    // Bold 'a' letter
    drawCircle(dark, w * 0.22f, Offset(cx - w * 0.15f, cy - h * 0.08f), style = Stroke(width = w * 0.09f))
    drawRoundRect(
        color = dark,
        topLeft = Offset(cx, cy - h * 0.3f),
        size = Size(w * 0.09f, h * 0.44f),
        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
    )

    // The Smile Arrow
    val smile = Path().apply {
        moveTo(cx - w * 0.38f, cy + h * 0.22f)
        quadraticBezierTo(cx - w * 0.05f, cy + h * 0.38f, cx + w * 0.32f, cy + h * 0.22f)
    }
    drawPath(smile, orange, style = Stroke(width = w * 0.06f, cap = StrokeCap.Round))

    // Arrowhead tip
    val tip = Path().apply {
        moveTo(cx + w * 0.26f, cy + h * 0.14f)
        lineTo(cx + w * 0.38f, cy + h * 0.23f)
        lineTo(cx + w * 0.28f, cy + h * 0.31f)
        close()
    }
    drawPath(tip, orange)
}

private fun DrawScope.drawGoogleLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val blue = Color(0xFF4285F4)
    val red = Color(0xFFEA4335)
    val yellow = Color(0xFFFBBC05)
    val green = Color(0xFF34A853)

    val r = w * 0.38f
    val stroke = w * 0.16f

    // Draw Google 4-color arcs
    drawArc(
        color = red,
        startAngle = 210f,
        sweepAngle = 100f,
        useCenter = false,
        topLeft = Offset(cx - r, cy - r),
        size = Size(r * 2, r * 2),
        style = Stroke(stroke)
    )
    drawArc(
        color = yellow,
        startAngle = 120f,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = Offset(cx - r, cy - r),
        size = Size(r * 2, r * 2),
        style = Stroke(stroke)
    )
    drawArc(
        color = green,
        startAngle = 30f,
        sweepAngle = 90f,
        useCenter = false,
        topLeft = Offset(cx - r, cy - r),
        size = Size(r * 2, r * 2),
        style = Stroke(stroke)
    )
    drawArc(
        color = blue,
        startAngle = 310f,
        sweepAngle = 80f,
        useCenter = false,
        topLeft = Offset(cx - r, cy - r),
        size = Size(r * 2, r * 2),
        style = Stroke(stroke)
    )

    // Center crossbar
    drawRoundRect(
        color = blue,
        topLeft = Offset(cx, cy - stroke / 2),
        size = Size(r + stroke / 2, stroke),
        cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
    )
}

private fun DrawScope.drawMcdonaldsLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFDA291C)
    val yellow = Color(0xFFFFC72C)

    // Rounded Red Background Card
    drawRoundRect(
        color = red,
        topLeft = Offset(cx - w * 0.42f, cy - h * 0.42f),
        size = Size(w * 0.84f, h * 0.84f),
        cornerRadius = CornerRadius(20.dp.toPx(), 20.dp.toPx())
    )

    val strokeWidth = w * 0.1f
    // Left Golden Arch
    val leftArch = Path().apply {
        moveTo(cx - w * 0.32f, cy + h * 0.28f)
        cubicTo(
            cx - w * 0.32f, cy - h * 0.28f,
            cx - w * 0.05f, cy - h * 0.28f,
            cx - w * 0.02f, cy + h * 0.28f
        )
    }
    drawPath(leftArch, yellow, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))

    // Right Golden Arch
    val rightArch = Path().apply {
        moveTo(cx + w * 0.02f, cy + h * 0.28f)
        cubicTo(
            cx + w * 0.05f, cy - h * 0.28f,
            cx + w * 0.32f, cy - h * 0.28f,
            cx + w * 0.32f, cy + h * 0.28f
        )
    }
    drawPath(rightArch, yellow, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
}

private fun DrawScope.drawZohoLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val boxSize = w * 0.28f
    val gap = w * 0.04f
    val cornerRadius = CornerRadius(boxSize * 0.2f, boxSize * 0.2f)

    // Red (top-left)
    drawRoundRect(
        color = Color(0xFFE32726),
        topLeft = Offset(cx - boxSize - gap / 2f, cy - boxSize - gap / 2f),
        size = Size(boxSize, boxSize),
        cornerRadius = cornerRadius
    )
    // Green (top-right)
    drawRoundRect(
        color = Color(0xFF319842),
        topLeft = Offset(cx + gap / 2f, cy - boxSize - gap / 2f),
        size = Size(boxSize, boxSize),
        cornerRadius = cornerRadius
    )
    // Blue (bottom-left)
    drawRoundRect(
        color = Color(0xFF006BB4),
        topLeft = Offset(cx - boxSize - gap / 2f, cy + gap / 2f),
        size = Size(boxSize, boxSize),
        cornerRadius = cornerRadius
    )
    // Yellow (bottom-right)
    drawRoundRect(
        color = Color(0xFFECA824),
        topLeft = Offset(cx + gap / 2f, cy + gap / 2f),
        size = Size(boxSize, boxSize),
        cornerRadius = cornerRadius
    )
}

// ----------------- ENTERTAINMENT -----------------

private fun DrawScope.drawNetflixLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val darkRed = Color(0xFFB81D24)
    val brightRed = Color(0xFFE50914)
    val bgDark = Color(0xFF141414)

    // Dark backdrop rounded
    drawRoundRect(
        color = bgDark,
        topLeft = Offset(cx - w * 0.38f, cy - h * 0.42f),
        size = Size(w * 0.76f, h * 0.84f),
        cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx())
    )

    val colW = w * 0.16f
    // Left ribbon column
    drawRect(
        color = darkRed,
        topLeft = Offset(cx - w * 0.24f, cy - h * 0.32f),
        size = Size(colW, h * 0.64f)
    )
    // Right ribbon column
    drawRect(
        color = darkRed,
        topLeft = Offset(cx + w * 0.08f, cy - h * 0.32f),
        size = Size(colW, h * 0.64f)
    )
    // Diagonal ribbon with bright red
    val diag = Path().apply {
        moveTo(cx - w * 0.24f, cy - h * 0.32f)
        lineTo(cx - w * 0.24f + colW, cy - h * 0.32f)
        lineTo(cx + w * 0.24f, cy + h * 0.32f)
        lineTo(cx + w * 0.24f - colW, cy + h * 0.32f)
        close()
    }
    drawPath(diag, brightRed)
}

private fun DrawScope.drawDisneyLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val blue = Color(0xFF113CCF)
    // Castle base & spires silhouette
    val castle = Path().apply {
        // Base
        moveTo(cx - w * 0.32f, cy + h * 0.25f)
        lineTo(cx - w * 0.32f, cy)
        lineTo(cx - w * 0.24f, cy - h * 0.18f)
        lineTo(cx - w * 0.16f, cy)
        lineTo(cx - w * 0.08f, cy - h * 0.08f)
        // High Central Tower
        lineTo(cx - w * 0.06f, cy - h * 0.12f)
        lineTo(cx, cy - h * 0.38f)
        lineTo(cx + w * 0.06f, cy - h * 0.12f)
        lineTo(cx + w * 0.08f, cy - h * 0.08f)
        lineTo(cx + w * 0.16f, cy)
        lineTo(cx + w * 0.24f, cy - h * 0.18f)
        lineTo(cx + w * 0.32f, cy)
        lineTo(cx + w * 0.32f, cy + h * 0.25f)
        close()
    }
    drawPath(castle, blue)

    // Arch over castle (shooting star arc)
    drawArc(
        color = blue,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(cx - w * 0.4f, cy - h * 0.38f),
        size = Size(w * 0.8f, h * 0.65f),
        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
    )
}

private fun DrawScope.drawMarvelLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFED1D24)
    // Bold Red Rectangle
    drawRoundRect(
        color = red,
        topLeft = Offset(cx - w * 0.44f, cy - h * 0.25f),
        size = Size(w * 0.88f, h * 0.5f),
        cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
    )
    // White comic letter 'M' silhouette
    val mPath = Path().apply {
        moveTo(cx - w * 0.28f, cy + h * 0.16f)
        lineTo(cx - w * 0.28f, cy - h * 0.16f)
        lineTo(cx - w * 0.15f, cy - h * 0.16f)
        lineTo(cx, cy + h * 0.02f)
        lineTo(cx + w * 0.15f, cy - h * 0.16f)
        lineTo(cx + w * 0.28f, cy - h * 0.16f)
        lineTo(cx + w * 0.28f, cy + h * 0.16f)
        lineTo(cx + w * 0.19f, cy + h * 0.16f)
        lineTo(cx + w * 0.19f, cy - h * 0.02f)
        lineTo(cx, cy + h * 0.16f)
        lineTo(cx - w * 0.19f, cy - h * 0.02f)
        lineTo(cx - w * 0.19f, cy + h * 0.16f)
        close()
    }
    drawPath(mPath, Color.White)
}

private fun DrawScope.drawYoutubeLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFFF0000)
    // Rounded Red screen badge
    drawRoundRect(
        color = red,
        topLeft = Offset(cx - w * 0.4f, cy - h * 0.28f),
        size = Size(w * 0.8f, h * 0.56f),
        cornerRadius = CornerRadius(22.dp.toPx(), 22.dp.toPx())
    )
    // White Play Triangle
    val triangle = Path().apply {
        moveTo(cx - w * 0.12f, cy - h * 0.15f)
        lineTo(cx + w * 0.18f, cy)
        lineTo(cx - w * 0.12f, cy + h * 0.15f)
        close()
    }
    drawPath(triangle, Color.White)
}

private fun DrawScope.drawPixarLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val dark = Color(0xFF334155)
    // Desk lamp (Luxo Jr.) silhouette
    // Base oval
    drawOval(dark, topLeft = Offset(cx - w * 0.25f, cy + h * 0.25f), size = Size(w * 0.5f, h * 0.12f))

    // Angled arms
    val strokeWidth = w * 0.045f
    drawLine(dark, Offset(cx, cy + h * 0.25f), Offset(cx - w * 0.18f, cy + h * 0.02f), strokeWidth = strokeWidth, cap = StrokeCap.Round)
    drawLine(dark, Offset(cx - w * 0.18f, cy + h * 0.02f), Offset(cx + w * 0.05f, cy - h * 0.18f), strokeWidth = strokeWidth, cap = StrokeCap.Round)

    // Lamp Shade cone
    val shade = Path().apply {
        moveTo(cx + w * 0.05f, cy - h * 0.18f)
        lineTo(cx + w * 0.32f, cy - h * 0.32f)
        lineTo(cx + w * 0.22f, cy - h * 0.02f)
        close()
    }
    drawPath(shade, dark)
}

private fun DrawScope.drawBatmanLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val yellow = Color(0xFFFBBF24)
    val black = Color(0xFF0F172A)
    // Yellow Oval
    drawOval(yellow, topLeft = Offset(cx - w * 0.44f, cy - h * 0.28f), size = Size(w * 0.88f, h * 0.56f))

    // Sharp bat silhouette
    val bat = Path().apply {
        moveTo(cx, cy - h * 0.15f)
        lineTo(cx - w * 0.06f, cy - h * 0.24f)
        lineTo(cx - w * 0.04f, cy - h * 0.14f)
        quadraticBezierTo(cx - w * 0.22f, cy - h * 0.22f, cx - w * 0.38f, cy - h * 0.1f)
        quadraticBezierTo(cx - w * 0.32f, cy + h * 0.15f, cx - w * 0.18f, cy + h * 0.08f)
        quadraticBezierTo(cx - w * 0.12f, cy + h * 0.22f, cx, cy + h * 0.16f)
        quadraticBezierTo(cx + w * 0.12f, cy + h * 0.22f, cx + w * 0.18f, cy + h * 0.08f)
        quadraticBezierTo(cx + w * 0.32f, cy + h * 0.15f, cx + w * 0.38f, cy - h * 0.1f)
        quadraticBezierTo(cx + w * 0.22f, cy - h * 0.22f, cx + w * 0.04f, cy - h * 0.14f)
        lineTo(cx + w * 0.06f, cy - h * 0.24f)
        close()
    }
    drawPath(bat, black)
}

private fun DrawScope.drawPotterLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val dark = Color(0xFF1E293B)
    val gold = Color(0xFFD97706)

    // Lightning bolt scar above
    val scar = Path().apply {
        moveTo(cx - w * 0.05f, cy - h * 0.36f)
        lineTo(cx + w * 0.12f, cy - h * 0.36f)
        lineTo(cx - w * 0.02f, cy - h * 0.2f)
        lineTo(cx + w * 0.14f, cy - h * 0.2f)
        lineTo(cx - w * 0.08f, cy - h * 0.05f)
        lineTo(cx + w * 0.01f, cy - h * 0.16f)
        lineTo(cx - w * 0.08f, cy - h * 0.16f)
        close()
    }
    drawPath(scar, gold)

    // Round Spectacles
    val r = w * 0.18f
    val stroke = 4.dp.toPx()
    drawCircle(dark, r, Offset(cx - w * 0.2f, cy + h * 0.1f), style = Stroke(stroke))
    drawCircle(dark, r, Offset(cx + w * 0.2f, cy + h * 0.1f), style = Stroke(stroke))
    // Bridge
    drawLine(dark, Offset(cx - w * 0.05f, cy + h * 0.06f), Offset(cx + w * 0.05f, cy + h * 0.06f), strokeWidth = stroke, cap = StrokeCap.Round)
}

private fun DrawScope.drawWarnerLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val blue = Color(0xFF003087)
    val gold = Color(0xFFFFCC00)

    // Shield outline
    val shield = Path().apply {
        moveTo(cx - w * 0.35f, cy - h * 0.32f)
        lineTo(cx + w * 0.35f, cy - h * 0.32f)
        cubicTo(
            cx + w * 0.38f, cy + h * 0.1f,
            cx + w * 0.2f, cy + h * 0.35f,
            cx, cy + h * 0.42f
        )
        cubicTo(
            cx - w * 0.2f, cy + h * 0.35f,
            cx - w * 0.38f, cy + h * 0.1f,
            cx - w * 0.35f, cy - h * 0.32f
        )
        close()
    }
    drawPath(shield, blue)
    drawPath(shield, gold, style = Stroke(width = 6.dp.toPx()))

    // Stylized WB Banner Bar
    drawRoundRect(
        color = gold,
        topLeft = Offset(cx - w * 0.28f, cy - h * 0.08f),
        size = Size(w * 0.56f, h * 0.16f),
        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
    )
}

private fun DrawScope.drawHboLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val dark = Color(0xFF000000)
    // Giant O with inner static circle
    val r = w * 0.32f
    drawCircle(dark, r, Offset(cx, cy))
    drawCircle(Color.White, r * 0.65f, Offset(cx, cy))
    drawCircle(dark, r * 0.32f, Offset(cx, cy))
}

private fun DrawScope.drawTwitchLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val purple = Color(0xFF9146FF)
    // Glitch chat mascot bubble
    val chat = Path().apply {
        moveTo(cx - w * 0.36f, cy - h * 0.34f)
        lineTo(cx + w * 0.36f, cy - h * 0.34f)
        lineTo(cx + w * 0.36f, cy + h * 0.18f)
        lineTo(cx + w * 0.18f, cy + h * 0.18f)
        lineTo(cx + w * 0.06f, cy + h * 0.34f)
        lineTo(cx + w * 0.06f, cy + h * 0.18f)
        lineTo(cx - w * 0.16f, cy + h * 0.18f)
        lineTo(cx - w * 0.36f, cy - h * 0.02f)
        close()
    }
    drawPath(chat, purple)

    // Glitch Eyes
    val eyeW = w * 0.07f
    val eyeH = h * 0.18f
    drawRect(Color.White, topLeft = Offset(cx - w * 0.12f, cy - h * 0.16f), size = Size(eyeW, eyeH))
    drawRect(Color.White, topLeft = Offset(cx + w * 0.08f, cy - h * 0.16f), size = Size(eyeW, eyeH))
}

// ----------------- GAMING & TECH -----------------

private fun DrawScope.drawNintendoLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFE60012)
    // Racetrack capsule border
    drawRoundRect(
        color = red,
        topLeft = Offset(cx - w * 0.44f, cy - h * 0.22f),
        size = Size(w * 0.88f, h * 0.44f),
        cornerRadius = CornerRadius(24.dp.toPx(), 24.dp.toPx()),
        style = Stroke(width = 6.dp.toPx())
    )
    // Stylized Switch Joy-Cons
    drawRoundRect(
        color = red,
        topLeft = Offset(cx - w * 0.28f, cy - h * 0.14f),
        size = Size(w * 0.22f, h * 0.28f),
        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
    )
    drawCircle(Color.White, w * 0.045f, Offset(cx - w * 0.17f, cy - h * 0.04f))

    drawRoundRect(
        color = red,
        topLeft = Offset(cx + w * 0.06f, cy - h * 0.14f),
        size = Size(w * 0.22f, h * 0.28f),
        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
    )
    drawCircle(Color.White, w * 0.045f, Offset(cx + w * 0.17f, cy + h * 0.04f))
}

private fun DrawScope.drawPlaystationLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFDF0024)
    val yellow = Color(0xFFF3C300)
    val green = Color(0xFF00AC9F)
    val blue = Color(0xFF2E6DB4)

    // Standing P
    val pPath = Path().apply {
        moveTo(cx - w * 0.14f, cy - h * 0.35f)
        lineTo(cx + w * 0.08f, cy - h * 0.35f)
        cubicTo(cx + w * 0.3f, cy - h * 0.35f, cx + w * 0.3f, cy - h * 0.08f, cx + w * 0.08f, cy - h * 0.08f)
        lineTo(cx - w * 0.02f, cy - h * 0.08f)
        lineTo(cx - w * 0.02f, cy + h * 0.12f)
        lineTo(cx - w * 0.14f, cy + h * 0.12f)
        close()
    }
    drawPath(pPath, red)

    // Fallen S shadow on floor
    val sShadow = Path().apply {
        moveTo(cx - w * 0.36f, cy + h * 0.15f)
        cubicTo(cx - w * 0.1f, cy + h * 0.05f, cx + w * 0.25f, cy + h * 0.12f, cx + w * 0.36f, cy + h * 0.28f)
        cubicTo(cx + w * 0.18f, cy + h * 0.38f, cx - w * 0.15f, cy + h * 0.32f, cx - w * 0.36f, cy + h * 0.15f)
        close()
    }
    drawPath(sShadow, blue)
    drawCircle(yellow, w * 0.045f, Offset(cx - w * 0.12f, cy + h * 0.22f))
    drawCircle(green, w * 0.045f, Offset(cx + w * 0.15f, cy + h * 0.22f))
}

private fun DrawScope.drawXboxLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val lime = Color(0xFF107C10)
    val r = w * 0.4f
    drawCircle(lime, r, Offset(cx, cy))

    // 3D Silver Cutout 'X'
    val xPath = Path().apply {
        moveTo(cx - r * 0.7f, cy - r * 0.7f)
        quadraticBezierTo(cx, cy - r * 0.1f, cx + r * 0.7f, cy - r * 0.7f)
        quadraticBezierTo(cx + r * 0.1f, cy, cx + r * 0.7f, cy + r * 0.7f)
        quadraticBezierTo(cx, cy + r * 0.1f, cx - r * 0.7f, cy + r * 0.7f)
        quadraticBezierTo(cx - r * 0.1f, cy, cx - r * 0.7f, cy - r * 0.7f)
        close()
    }
    drawPath(xPath, Color(0xFFF1F5F9))
}

private fun DrawScope.drawSteamLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val darkBlue = Color(0xFF171A21)
    val lightBlue = Color(0xFF66C0F4)
    val r = w * 0.42f
    drawCircle(darkBlue, r, Offset(cx, cy))

    // Locomotive arm & linkage
    val strokeWidth = w * 0.08f
    drawLine(lightBlue, Offset(cx + r * 0.25f, cy - r * 0.25f), Offset(cx - r * 0.35f, cy + r * 0.15f), strokeWidth = strokeWidth, cap = StrokeCap.Round)

    // Big piston joint
    drawCircle(lightBlue, r * 0.32f, Offset(cx + r * 0.25f, cy - r * 0.25f), style = Stroke(width = strokeWidth * 0.7f))
    // Small connecting rod joint
    drawCircle(lightBlue, r * 0.18f, Offset(cx - r * 0.35f, cy + r * 0.15f))
    drawCircle(darkBlue, r * 0.08f, Offset(cx - r * 0.35f, cy + r * 0.15f))
}

private fun DrawScope.drawDiscordLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val blurple = Color(0xFF5865F2)
    // Clyde Controller Mascot
    val clyde = Path().apply {
        moveTo(cx - w * 0.36f, cy - h * 0.18f)
        cubicTo(cx - w * 0.36f, cy - h * 0.32f, cx - w * 0.2f, cy - h * 0.26f, cx, cy - h * 0.26f)
        cubicTo(cx + w * 0.2f, cy - h * 0.26f, cx + w * 0.36f, cy - h * 0.32f, cx + w * 0.36f, cy - h * 0.18f)
        cubicTo(cx + w * 0.4f, cy + h * 0.24f, cx + w * 0.24f, cy + h * 0.3f, cx + w * 0.18f, cy + h * 0.22f)
        lineTo(cx + w * 0.12f, cy + h * 0.26f)
        lineTo(cx - w * 0.12f, cy + h * 0.26f)
        lineTo(cx - w * 0.18f, cy + h * 0.22f)
        cubicTo(cx - w * 0.24f, cy + h * 0.3f, cx - w * 0.4f, cy + h * 0.24f, cx - w * 0.36f, cy - h * 0.18f)
        close()
    }
    drawPath(clyde, blurple)

    // Clyde eyes
    drawCircle(Color.White, w * 0.065f, Offset(cx - w * 0.14f, cy - h * 0.04f))
    drawCircle(Color.White, w * 0.065f, Offset(cx + w * 0.14f, cy - h * 0.04f))
}

private fun DrawScope.drawAndroidLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val green = Color(0xFF3DDC84)
    val r = w * 0.32f

    // Bugdroid dome head
    drawArc(
        color = green,
        startAngle = 180f,
        sweepAngle = 180f,
        useCenter = true,
        topLeft = Offset(cx - r, cy - r * 0.6f),
        size = Size(r * 2, r * 2)
    )

    // Antennae
    val antStroke = 4.dp.toPx()
    drawLine(green, Offset(cx - r * 0.5f, cy - r * 0.4f), Offset(cx - r * 0.8f, cy - r * 0.85f), strokeWidth = antStroke, cap = StrokeCap.Round)
    drawLine(green, Offset(cx + r * 0.5f, cy - r * 0.4f), Offset(cx + r * 0.8f, cy - r * 0.85f), strokeWidth = antStroke, cap = StrokeCap.Round)

    // Eyes
    drawCircle(Color.White, w * 0.045f, Offset(cx - r * 0.4f, cy))
    drawCircle(Color.White, w * 0.045f, Offset(cx + r * 0.4f, cy))

    // Collar / body curve
    drawRoundRect(
        color = green,
        topLeft = Offset(cx - r, cy + r * 0.52f),
        size = Size(r * 2, r * 0.6f),
        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
    )
}

private fun DrawScope.drawAtariLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFE41E26)
    val barW = w * 0.08f

    // Center vertical pillar
    drawRect(red, topLeft = Offset(cx - barW / 2, cy - h * 0.35f), size = Size(barW, h * 0.7f))

    // Left curved prong
    val leftProng = Path().apply {
        moveTo(cx - w * 0.12f, cy - h * 0.35f)
        cubicTo(cx - w * 0.12f, cy - h * 0.1f, cx - w * 0.35f, cy + h * 0.15f, cx - w * 0.38f, cy + h * 0.35f)
        lineTo(cx - w * 0.28f, cy + h * 0.35f)
        cubicTo(cx - w * 0.25f, cy + h * 0.15f, cx - w * 0.2f, cy - h * 0.1f, cx - w * 0.2f, cy - h * 0.35f)
        close()
    }
    drawPath(leftProng, red)

    // Right curved prong
    val rightProng = Path().apply {
        moveTo(cx + w * 0.12f, cy - h * 0.35f)
        cubicTo(cx + w * 0.12f, cy - h * 0.1f, cx + w * 0.35f, cy + h * 0.15f, cx + w * 0.38f, cy + h * 0.35f)
        lineTo(cx + w * 0.28f, cy + h * 0.35f)
        cubicTo(cx + w * 0.25f, cy + h * 0.15f, cx + w * 0.2f, cy - h * 0.1f, cx + w * 0.2f, cy - h * 0.35f)
        close()
    }
    drawPath(rightProng, red)
}

private fun DrawScope.drawSegaLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val blue = Color(0xFF004494)
    // Striped futuristic SEGA badge
    val barH = h * 0.07f
    val gap = h * 0.05f
    val startY = cy - h * 0.28f
    for (i in 0..5) {
        drawRoundRect(
            color = blue,
            topLeft = Offset(cx - w * 0.38f + (i * w * 0.03f), startY + i * (barH + gap)),
            size = Size(w * 0.76f - (i * w * 0.02f), barH),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
        )
    }
}

private fun DrawScope.drawRobloxLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val dark = Color(0xFF232527)
    // Tilted square ring
    val square = Path().apply {
        moveTo(cx, cy - h * 0.35f)
        lineTo(cx + w * 0.35f, cy)
        lineTo(cx, cy + h * 0.35f)
        lineTo(cx - w * 0.35f, cy)
        close()
    }
    drawPath(square, dark)

    // Hole inside
    val inner = Path().apply {
        moveTo(cx, cy - h * 0.12f)
        lineTo(cx + w * 0.12f, cy)
        lineTo(cx, cy + h * 0.12f)
        lineTo(cx - w * 0.12f, cy)
        close()
    }
    drawPath(inner, Color.White)
}

private fun DrawScope.drawLinuxLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val black = Color(0xFF1E293B)
    val yellow = Color(0xFFFBBF24)

    // Tux Body
    drawOval(black, topLeft = Offset(cx - w * 0.26f, cy - h * 0.25f), size = Size(w * 0.52f, h * 0.55f))
    // White belly
    drawOval(Color.White, topLeft = Offset(cx - w * 0.18f, cy - h * 0.12f), size = Size(w * 0.36f, h * 0.4f))

    // Yellow beak
    val beak = Path().apply {
        moveTo(cx - w * 0.08f, cy - h * 0.14f)
        lineTo(cx + w * 0.08f, cy - h * 0.14f)
        lineTo(cx, cy - h * 0.06f)
        close()
    }
    drawPath(beak, yellow)

    // Feet
    drawOval(yellow, topLeft = Offset(cx - w * 0.32f, cy + h * 0.25f), size = Size(w * 0.28f, h * 0.12f))
    drawOval(yellow, topLeft = Offset(cx + w * 0.04f, cy + h * 0.25f), size = Size(w * 0.28f, h * 0.12f))
}

// ----------------- SPORTS & AUTOS -----------------

private fun DrawScope.drawFerrariLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val yellow = Color(0xFFFFEB3B)
    val green = Color(0xFF008D46)
    val red = Color(0xFFD40000)
    val black = Color(0xFF111827)

    // Yellow Shield
    val shield = Path().apply {
        moveTo(cx - w * 0.32f, cy - h * 0.36f)
        lineTo(cx + w * 0.32f, cy - h * 0.36f)
        lineTo(cx + w * 0.32f, cy + h * 0.08f)
        cubicTo(cx + w * 0.32f, cy + h * 0.32f, cx + w * 0.15f, cy + h * 0.42f, cx, cy + h * 0.45f)
        cubicTo(cx - w * 0.15f, cy + h * 0.42f, cx - w * 0.32f, cy + h * 0.32f, cx - w * 0.32f, cy + h * 0.08f)
        close()
    }
    drawPath(shield, yellow)
    drawPath(shield, black, style = Stroke(width = 3.dp.toPx()))

    // Italian Tricolor Header
    val flagH = h * 0.06f
    val flagW = w * 0.64f
    drawRect(green, topLeft = Offset(cx - w * 0.32f, cy - h * 0.36f), size = Size(flagW / 3, flagH))
    drawRect(Color.White, topLeft = Offset(cx - w * 0.32f + flagW / 3, cy - h * 0.36f), size = Size(flagW / 3, flagH))
    drawRect(red, topLeft = Offset(cx - w * 0.32f + 2 * flagW / 3, cy - h * 0.36f), size = Size(flagW / 3, flagH))

    // Prancing Horse silhouette
    val horse = Path().apply {
        moveTo(cx - w * 0.04f, cy - h * 0.18f)
        lineTo(cx + w * 0.08f, cy - h * 0.14f)
        lineTo(cx + w * 0.02f, cy - h * 0.05f)
        lineTo(cx + w * 0.12f, cy - h * 0.08f)
        lineTo(cx + w * 0.06f, cy + h * 0.08f)
        lineTo(cx + w * 0.14f, cy + h * 0.28f)
        lineTo(cx + w * 0.04f, cy + h * 0.28f)
        lineTo(cx - w * 0.02f, cy + h * 0.14f)
        lineTo(cx - w * 0.1f, cy + h * 0.28f)
        lineTo(cx - w * 0.18f, cy + h * 0.28f)
        lineTo(cx - w * 0.08f, cy + h * 0.06f)
        lineTo(cx - w * 0.15f, cy - h * 0.08f)
        close()
    }
    drawPath(horse, black)
}

private fun DrawScope.drawAdidasLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val dark = Color(0xFF0F172A)
    // 3 Angled mountain stripes
    val stripeW = w * 0.1f

    // 1st stripe (shortest)
    val s1 = Path().apply {
        moveTo(cx - w * 0.32f, cy + h * 0.22f)
        lineTo(cx - w * 0.18f, cy + h * 0.08f)
        lineTo(cx - w * 0.18f + stripeW, cy + h * 0.08f)
        lineTo(cx - w * 0.32f + stripeW, cy + h * 0.22f)
        close()
    }
    drawPath(s1, dark)

    // 2nd stripe (medium)
    val s2 = Path().apply {
        moveTo(cx - w * 0.14f, cy + h * 0.22f)
        lineTo(cx + w * 0.08f, cy - h * 0.06f)
        lineTo(cx + w * 0.08f + stripeW, cy - h * 0.06f)
        lineTo(cx - w * 0.14f + stripeW, cy + h * 0.22f)
        close()
    }
    drawPath(s2, dark)

    // 3rd stripe (tallest)
    val s3 = Path().apply {
        moveTo(cx + w * 0.04f, cy + h * 0.22f)
        lineTo(cx + w * 0.32f, cy - h * 0.22f)
        lineTo(cx + w * 0.32f + stripeW, cy - h * 0.22f)
        lineTo(cx + w * 0.04f + stripeW, cy + h * 0.22f)
        close()
    }
    drawPath(s3, dark)
}

private fun DrawScope.drawPumaLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val dark = Color(0xFF1E293B)
    // Leaping puma cat in mid-air
    val cat = Path().apply {
        moveTo(cx - w * 0.35f, cy + h * 0.22f)
        cubicTo(cx - w * 0.32f, cy + h * 0.08f, cx - w * 0.12f, cy - h * 0.15f, cx + w * 0.12f, cy - h * 0.28f)
        lineTo(cx + w * 0.35f, cy - h * 0.32f)
        lineTo(cx + w * 0.28f, cy - h * 0.18f)
        cubicTo(cx + w * 0.15f, cy - h * 0.1f, cx + w * 0.02f, cy + h * 0.08f, cx - w * 0.12f, cy + h * 0.22f)
        close()
    }
    drawPath(cat, dark)
}

private fun DrawScope.drawOlympicLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val r = w * 0.16f
    val stroke = 4.dp.toPx()
    val blue = Color(0xFF0085C7)
    val yellow = Color(0xFFF4C300)
    val black = Color(0xFF000000)
    val green = Color(0xFF009F3D)
    val red = Color(0xFFDF0024)

    // Top 3 rings
    drawCircle(blue, r, Offset(cx - r * 2.2f, cy - r * 0.4f), style = Stroke(stroke))
    drawCircle(black, r, Offset(cx, cy - r * 0.4f), style = Stroke(stroke))
    drawCircle(red, r, Offset(cx + r * 2.2f, cy - r * 0.4f), style = Stroke(stroke))

    // Bottom 2 interlocking rings
    drawCircle(yellow, r, Offset(cx - r * 1.1f, cy + r * 0.7f), style = Stroke(stroke))
    drawCircle(green, r, Offset(cx + r * 1.1f, cy + r * 0.7f), style = Stroke(stroke))
}

private fun DrawScope.drawNbaLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFD4002C)
    val blue = Color(0xFF1D428A)

    // Vertical Rounded Badge
    val badgeW = w * 0.44f
    val badgeH = h * 0.78f
    val x = cx - badgeW / 2
    val y = cy - badgeH / 2

    drawRect(blue, topLeft = Offset(x, y), size = Size(badgeW / 2, badgeH))
    drawRect(red, topLeft = Offset(cx, y), size = Size(badgeW / 2, badgeH))

    // White Dribbling Player silhouette
    drawCircle(Color.White, w * 0.05f, Offset(cx - w * 0.04f, cy - h * 0.22f))
    val body = Path().apply {
        moveTo(cx - w * 0.02f, cy - h * 0.14f)
        lineTo(cx + w * 0.05f, cy + h * 0.02f)
        lineTo(cx + w * 0.12f, cy + h * 0.28f)
        lineTo(cx + w * 0.04f, cy + h * 0.28f)
        lineTo(cx - w * 0.04f, cy + h * 0.12f)
        lineTo(cx - w * 0.12f, cy + h * 0.28f)
        lineTo(cx - w * 0.18f, cy + h * 0.28f)
        close()
    }
    drawPath(body, Color.White)
    // Basketball
    drawCircle(Color.White, w * 0.04f, Offset(cx - w * 0.14f, cy - h * 0.04f))
}

private fun DrawScope.drawRedbullLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val yellow = Color(0xFFFFCC00)
    val red = Color(0xFFDC0032)

    // Yellow Sun
    drawCircle(yellow, w * 0.25f, Offset(cx, cy))

    // Left Bull
    val leftBull = Path().apply {
        moveTo(cx - w * 0.38f, cy + h * 0.15f)
        lineTo(cx - w * 0.22f, cy - h * 0.05f)
        lineTo(cx - w * 0.05f, cy - h * 0.08f)
        lineTo(cx - w * 0.12f, cy + h * 0.18f)
        close()
    }
    drawPath(leftBull, red)

    // Right Bull
    val rightBull = Path().apply {
        moveTo(cx + w * 0.38f, cy + h * 0.15f)
        lineTo(cx + w * 0.22f, cy - h * 0.05f)
        lineTo(cx + w * 0.05f, cy - h * 0.08f)
        lineTo(cx + w * 0.12f, cy + h * 0.18f)
        close()
    }
    drawPath(rightBull, red)
}

private fun DrawScope.drawBmwLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val r = w * 0.4f
    val dark = Color(0xFF0F172A)
    val blue = Color(0xFF0066B1)

    // Outer Dark Ring
    drawCircle(dark, r, Offset(cx, cy))
    drawCircle(Color.White, r * 0.72f, Offset(cx, cy))

    // Inner 4 quadrants
    val innerR = r * 0.68f
    drawArc(blue, 180f, 90f, true, topLeft = Offset(cx - innerR, cy - innerR), size = Size(innerR * 2, innerR * 2))
    drawArc(Color.White, 270f, 90f, true, topLeft = Offset(cx - innerR, cy - innerR), size = Size(innerR * 2, innerR * 2))
    drawArc(blue, 0f, 90f, true, topLeft = Offset(cx - innerR, cy - innerR), size = Size(innerR * 2, innerR * 2))
    drawArc(Color.White, 90f, 90f, true, topLeft = Offset(cx - innerR, cy - innerR), size = Size(innerR * 2, innerR * 2))
}

private fun DrawScope.drawAudiLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val silver = Color(0xFF475569)
    val r = w * 0.15f
    val stroke = 5.dp.toPx()
    val gap = r * 1.35f

    for (i in -1..2) {
        val offsetX = cx + (i - 0.5f) * gap
        drawCircle(silver, r, Offset(offsetX, cy), style = Stroke(stroke))
    }
}

private fun DrawScope.drawMercedesLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val silver = Color(0xFF334155)
    val r = w * 0.4f
    val stroke = 4.dp.toPx()

    // Outer Ring
    drawCircle(silver, r, Offset(cx, cy), style = Stroke(stroke))

    // Three-pointed star rays (pointing up at 270 deg, bottom-right at 30 deg, bottom-left at 150 deg)
    val star = Path().apply {
        moveTo(cx, cy)
        lineTo(cx, cy - r * 0.95f)
        moveTo(cx, cy)
        lineTo(cx + r * 0.82f, cy + r * 0.48f)
        moveTo(cx, cy)
        lineTo(cx - r * 0.82f, cy + r * 0.48f)
    }
    drawPath(star, silver, style = Stroke(width = stroke * 1.5f, cap = StrokeCap.Round))
}

private fun DrawScope.drawFifaLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val blue = Color(0xFF003087)
    val gold = Color(0xFFF59E0B)
    val r = w * 0.28f

    // Two overlapping globe balls
    drawCircle(blue, r, Offset(cx - r * 0.55f, cy), style = Stroke(width = 4.dp.toPx()))
    drawCircle(blue, r, Offset(cx + r * 0.55f, cy), style = Stroke(width = 4.dp.toPx()))

    // Golden banner across
    drawRoundRect(
        color = gold,
        topLeft = Offset(cx - w * 0.38f, cy - h * 0.08f),
        size = Size(w * 0.76f, h * 0.16f),
        cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
    )
}

private fun DrawScope.drawGenericQuizLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val blue = Color(0xFF3B82F6)
    drawCircle(blue, w * 0.38f, Offset(cx, cy))
    // Question mark
    drawCircle(Color.White, w * 0.1f, Offset(cx, cy - h * 0.12f), style = Stroke(width = 4.dp.toPx()))
    drawCircle(Color.White, w * 0.05f, Offset(cx, cy + h * 0.18f))
}

// ----------------- FOOD & TREATS -----------------

private fun DrawScope.drawPepsiLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val r = w * 0.38f
    val red = Color(0xFFE32934)
    val blue = Color(0xFF004B93)

    // Red top hemisphere
    val topPath = Path().apply {
        moveTo(cx - r, cy)
        arcTo(
            rect = androidx.compose.ui.geometry.Rect(cx - r, cy - r, cx + r, cy + r),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 180f,
            forceMoveTo = false
        )
        // Wave back: curves down then up
        cubicTo(
            cx + r * 0.4f, cy - r * 0.2f,
            cx - r * 0.3f, cy + r * 0.15f,
            cx - r, cy
        )
        close()
    }
    drawPath(topPath, red)

    // Blue bottom hemisphere
    val bottomPath = Path().apply {
        moveTo(cx - r, cy + r * 0.15f)
        cubicTo(
            cx - r * 0.3f, cy + r * 0.3f,
            cx + r * 0.4f, cy - r * 0.05f,
            cx + r, cy + r * 0.1f
        )
        arcTo(
            rect = androidx.compose.ui.geometry.Rect(cx - r, cy - r, cx + r, cy + r),
            startAngleDegrees = 10f,
            sweepAngleDegrees = 170f,
            forceMoveTo = false
        )
        close()
    }
    drawPath(bottomPath, blue)
}

private fun DrawScope.drawBurgerKingLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val bunColor = Color(0xFFF59E0B)
    val red = Color(0xFFDC2626)
    val blueArc = Color(0xFF2563EB)
    val r = w * 0.36f

    // Blue surrounding tilted circular crest
    drawCircle(
        color = blueArc,
        radius = r * 1.05f,
        center = Offset(cx, cy),
        style = Stroke(width = 5.dp.toPx())
    )

    // Top Bun
    val topBun = Path().apply {
        moveTo(cx - r * 0.75f, cy - h * 0.08f)
        quadraticTo(cx, cy - h * 0.38f, cx + r * 0.75f, cy - h * 0.08f)
        close()
    }
    drawPath(topBun, bunColor)

    // Middle red flame-broiled burger patty
    drawRoundRect(
        color = red,
        topLeft = Offset(cx - r * 0.85f, cy - h * 0.05f),
        size = Size(r * 1.7f, h * 0.1f),
        cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
    )

    // Bottom Bun
    val bottomBun = Path().apply {
        moveTo(cx - r * 0.7f, cy + h * 0.09f)
        quadraticTo(cx, cy + h * 0.32f, cx + r * 0.7f, cy + h * 0.09f)
        close()
    }
    drawPath(bottomBun, bunColor)
}

private fun DrawScope.drawSubwayLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val green = Color(0xFF008938)
    val yellow = Color(0xFFFFC600)
    val white = Color.White

    // Green rounded container
    drawRoundRect(
        color = green,
        topLeft = Offset(cx - w * 0.44f, cy - h * 0.28f),
        size = Size(w * 0.88f, h * 0.56f),
        cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx())
    )

    // Top yellow forward arrow (pointing right)
    val yellowArrow = Path().apply {
        moveTo(cx - w * 0.28f, cy - h * 0.08f)
        lineTo(cx + w * 0.12f, cy - h * 0.08f)
        lineTo(cx + w * 0.12f, cy - h * 0.16f)
        lineTo(cx + w * 0.32f, cy - h * 0.04f)
        lineTo(cx + w * 0.12f, cy + h * 0.08f)
        lineTo(cx + w * 0.12f, cy)
        lineTo(cx - w * 0.28f, cy)
        close()
    }
    drawPath(yellowArrow, yellow)

    // Bottom white backward arrow (pointing left)
    val whiteArrow = Path().apply {
        moveTo(cx + w * 0.28f, cy + h * 0.1f)
        lineTo(cx - w * 0.12f, cy + h * 0.1f)
        lineTo(cx - w * 0.12f, cy + h * 0.18f)
        lineTo(cx - w * 0.32f, cy + h * 0.06f)
        lineTo(cx - w * 0.12f, cy - h * 0.06f)
        lineTo(cx - w * 0.12f, cy + h * 0.02f)
        lineTo(cx + w * 0.28f, cy + h * 0.02f)
        close()
    }
    drawPath(whiteArrow, white)
}

private fun DrawScope.drawPringlesLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val headColor = Color(0xFFFDE68A)
    val dark = Color(0xFF1E1B18)
    val red = Color(0xFFDC2626)
    val r = w * 0.35f

    // Oval Head
    drawOval(
        color = headColor,
        topLeft = Offset(cx - r * 0.9f, cy - r * 1.05f),
        size = Size(r * 1.8f, r * 2.1f)
    )

    // Two oval brown eyes
    drawOval(
        color = dark,
        topLeft = Offset(cx - r * 0.42f, cy - r * 0.45f),
        size = Size(r * 0.26f, r * 0.36f)
    )
    drawOval(
        color = dark,
        topLeft = Offset(cx + r * 0.16f, cy - r * 0.45f),
        size = Size(r * 0.26f, r * 0.36f)
    )

    // Iconic Handlebar Mustache
    val mustache = Path().apply {
        moveTo(cx, cy - r * 0.05f)
        quadraticTo(cx - r * 0.6f, cy - r * 0.3f, cx - r * 1.1f, cy + r * 0.1f)
        quadraticTo(cx - r * 0.55f, cy + r * 0.45f, cx, cy + r * 0.12f)
        quadraticTo(cx + r * 0.55f, cy + r * 0.45f, cx + r * 1.1f, cy + r * 0.1f)
        quadraticTo(cx + r * 0.6f, cy - r * 0.3f, cx, cy - r * 0.05f)
        close()
    }
    drawPath(mustache, dark)

    // Red Bow Tie
    val bowTie = Path().apply {
        moveTo(cx, cy + r * 0.48f)
        lineTo(cx - r * 0.55f, cy + r * 0.32f)
        lineTo(cx - r * 0.55f, cy + r * 0.72f)
        lineTo(cx, cy + r * 0.56f)
        lineTo(cx + r * 0.55f, cy + r * 0.72f)
        lineTo(cx + r * 0.55f, cy + r * 0.32f)
        close()
    }
    drawPath(bowTie, red)
    drawCircle(red, r * 0.14f, Offset(cx, cy + r * 0.52f))
}

private fun DrawScope.drawDominosLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFE11D48)
    val blue = Color(0xFF0284C7)
    val white = Color.White
    val sizePx = w * 0.48f

    // Save and rotate canvas 45 degrees
    drawContext.canvas.save()
    drawContext.transform.rotate(45f, Offset(cx, cy))

    // Top Red Half (with 1 dot)
    drawRoundRect(
        color = red,
        topLeft = Offset(cx - sizePx / 2f, cy - sizePx),
        size = Size(sizePx, sizePx * 0.98f),
        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
    )
    drawCircle(white, sizePx * 0.16f, Offset(cx, cy - sizePx * 0.51f))

    // Bottom Blue Half (with 2 dots)
    drawRoundRect(
        color = blue,
        topLeft = Offset(cx - sizePx / 2f, cy + sizePx * 0.02f),
        size = Size(sizePx, sizePx * 0.98f),
        cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
    )
    drawCircle(white, sizePx * 0.14f, Offset(cx - sizePx * 0.22f, cy + sizePx * 0.35f))
    drawCircle(white, sizePx * 0.14f, Offset(cx + sizePx * 0.22f, cy + sizePx * 0.67f))

    drawContext.canvas.restore()
}

private fun DrawScope.drawOreoLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val cookieDark = Color(0xFF27211C)
    val cream = Color(0xFFF8FAFC)
    val r = w * 0.38f

    // Outer notched chocolate cookie
    drawCircle(cookieDark, r, Offset(cx, cy))
    // Outer serrated ring
    drawCircle(
        color = Color(0xFF3F352E),
        radius = r * 0.92f,
        center = Offset(cx, cy),
        style = Stroke(width = 4.dp.toPx())
    )

    // Inner patterned ring
    drawCircle(
        color = Color(0xFF1B1612),
        radius = r * 0.65f,
        center = Offset(cx, cy),
        style = Stroke(width = 6.dp.toPx())
    )

    // Center cream peek / iconic cross
    drawRoundRect(
        color = cream,
        topLeft = Offset(cx - r * 0.45f, cy - r * 0.12f),
        size = Size(r * 0.9f, r * 0.24f),
        cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
    )
    drawRoundRect(
        color = cookieDark,
        topLeft = Offset(cx - r * 0.35f, cy - r * 0.06f),
        size = Size(r * 0.7f, r * 0.12f),
        cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
    )
}

private fun DrawScope.drawKfcLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val red = Color(0xFFDC2626)
    val white = Color.White
    val black = Color(0xFF1E293B)
    val r = w * 0.38f

    // Red and White striped bucket shield
    drawCircle(red, r, Offset(cx, cy))

    // White center stripe
    drawRect(
        color = white,
        topLeft = Offset(cx - r * 0.35f, cy - r),
        size = Size(r * 0.7f, r * 2f)
    )

    // Colonel's stylized hair & glasses silhouette
    // Glasses
    drawRoundRect(
        color = black,
        topLeft = Offset(cx - r * 0.48f, cy - r * 0.28f),
        size = Size(r * 0.4f, r * 0.24f),
        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
        style = Stroke(width = 3.dp.toPx())
    )
    drawRoundRect(
        color = black,
        topLeft = Offset(cx + r * 0.08f, cy - r * 0.28f),
        size = Size(r * 0.4f, r * 0.24f),
        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
        style = Stroke(width = 3.dp.toPx())
    )
    drawLine(black, Offset(cx - r * 0.08f, cy - r * 0.16f), Offset(cx + r * 0.08f, cy - r * 0.16f), strokeWidth = 3.dp.toPx())

    // Mustache and Goatee
    val mustache = Path().apply {
        moveTo(cx - r * 0.45f, cy + r * 0.05f)
        quadraticTo(cx, cy - r * 0.05f, cx + r * 0.45f, cy + r * 0.05f)
        quadraticTo(cx, cy + r * 0.2f, cx - r * 0.45f, cy + r * 0.05f)
        close()
    }
    drawPath(mustache, white)
    drawPath(mustache, black, style = Stroke(width = 2.dp.toPx()))

    // Black string tie
    val bowTie = Path().apply {
        moveTo(cx, cy + r * 0.32f)
        lineTo(cx - r * 0.25f, cy + r * 0.7f)
        lineTo(cx, cy + r * 0.45f)
        lineTo(cx + r * 0.25f, cy + r * 0.7f)
        close()
    }
    drawPath(bowTie, black)
}

private fun DrawScope.drawNutellaLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val spreadBrown = Color(0xFF3E2723)
    val white = Color.White
    val red = Color(0xFFDC2626)
    val dark = Color(0xFF1E1B18)
    val r = w * 0.36f

    // White Jar Lid
    drawRoundRect(
        color = Color(0xFFF1F5F9),
        topLeft = Offset(cx - r * 0.65f, cy - r * 1.05f),
        size = Size(r * 1.3f, r * 0.28f),
        cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
    )

    // Glass Jar filled with chocolate spread
    val jar = Path().apply {
        moveTo(cx - r * 0.75f, cy - r * 0.75f)
        lineTo(cx + r * 0.75f, cy - r * 0.75f)
        quadraticTo(cx + r * 0.95f, cy, cx + r * 0.85f, cy + r * 0.85f)
        quadraticTo(cx, cy + r * 1.05f, cx - r * 0.85f, cy + r * 0.85f)
        quadraticTo(cx - r * 0.95f, cy, cx - r * 0.75f, cy - r * 0.75f)
        close()
    }
    drawPath(jar, spreadBrown)

    // White label on front
    drawRoundRect(
        color = white,
        topLeft = Offset(cx - r * 0.72f, cy - r * 0.45f),
        size = Size(r * 1.44f, r * 0.85f),
        cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
    )

    // Iconic black 'n' and red 'utella' blocks
    drawRoundRect(
        color = dark,
        topLeft = Offset(cx - r * 0.55f, cy - r * 0.28f),
        size = Size(r * 0.32f, r * 0.5f),
        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
    )
    drawRoundRect(
        color = red,
        topLeft = Offset(cx - r * 0.15f, cy - r * 0.2f),
        size = Size(r * 0.65f, r * 0.35f),
        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
    )
}

private fun DrawScope.drawFantaLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val orange = Color(0xFFF97316)
    val green = Color(0xFF22C55E)
    val blue = Color(0xFF1D4ED8)
    val r = w * 0.34f

    // Green Leaf on top
    val leaf = Path().apply {
        moveTo(cx + r * 0.1f, cy - r * 0.7f)
        quadraticTo(cx + r * 0.7f, cy - r * 1.15f, cx + r * 0.85f, cy - r * 0.6f)
        quadraticTo(cx + r * 0.4f, cy - r * 0.3f, cx + r * 0.1f, cy - r * 0.7f)
        close()
    }
    drawPath(leaf, green)

    // Big Orange Citrus Circle
    drawCircle(orange, r, Offset(cx, cy + r * 0.1f))

    // Inner lighter orange wedge lines
    drawCircle(Color(0xFFFED7AA), r * 0.82f, Offset(cx, cy + r * 0.1f), style = Stroke(width = 3.dp.toPx()))

    // Blue Dynamic Ribbon Wave across center
    val splash = Path().apply {
        moveTo(cx - r * 1.1f, cy + r * 0.2f)
        quadraticTo(cx - r * 0.2f, cy - r * 0.5f, cx + r * 0.5f, cy - r * 0.05f)
        quadraticTo(cx + r * 0.95f, cy + r * 0.3f, cx + r * 1.15f, cy + r * 0.05f)
        quadraticTo(cx + r * 0.6f, cy + r * 0.6f, cx, cy + r * 0.4f)
        quadraticTo(cx - r * 0.6f, cy + r * 0.55f, cx - r * 1.1f, cy + r * 0.2f)
        close()
    }
    drawPath(splash, blue)
}

private fun DrawScope.drawTacobellLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val purple = Color(0xFF701A75)
    val magenta = Color(0xFFD946EF)
    val yellow = Color(0xFFFACC15)
    val r = w * 0.38f

    // Deep purple rounded arch
    drawRoundRect(
        color = purple,
        topLeft = Offset(cx - r, cy - r),
        size = Size(r * 2f, r * 2f),
        cornerRadius = CornerRadius(24.dp.toPx(), 24.dp.toPx())
    )

    // Magenta Bell silhouette
    val bell = Path().apply {
        moveTo(cx, cy - r * 0.65f)
        // Bell handle top
        cubicTo(cx - r * 0.12f, cy - r * 0.65f, cx - r * 0.15f, cy - r * 0.45f, cx - r * 0.3f, cy - r * 0.2f)
        // Bell body flare left
        quadraticTo(cx - r * 0.65f, cy + r * 0.2f, cx - r * 0.6f, cy + r * 0.45f)
        // Bell bottom rim
        quadraticTo(cx, cy + r * 0.32f, cx + r * 0.6f, cy + r * 0.45f)
        // Bell body flare right
        quadraticTo(cx + r * 0.65f, cy + r * 0.2f, cx + r * 0.3f, cy - r * 0.2f)
        cubicTo(cx + r * 0.15f, cy - r * 0.45f, cx + r * 0.12f, cy - r * 0.65f, cx, cy - r * 0.65f)
        close()
    }
    drawPath(bell, magenta)

    // Golden clapper hanging at bottom of bell
    drawCircle(yellow, r * 0.15f, Offset(cx, cy + r * 0.42f))
}

// ----------------- WORLD WONDERS -----------------

private fun DrawScope.drawEiffelLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val skyNavy = Color(0xFF0F172A)
    val bronze = Color(0xFFF59E0B)
    val r = w * 0.38f

    // Night sky circle
    drawCircle(skyNavy, r, Offset(cx, cy))

    // Eiffel Tower Lattice Path
    val tower = Path().apply {
        // Base legs
        moveTo(cx - r * 0.6f, cy + r * 0.85f)
        lineTo(cx - r * 0.35f, cy + r * 0.85f)
        // Lower Arch
        quadraticTo(cx, cy + r * 0.4f, cx + r * 0.35f, cy + r * 0.85f)
        lineTo(cx + r * 0.6f, cy + r * 0.85f)
        // Taper to 1st deck
        lineTo(cx + r * 0.38f, cy + r * 0.35f)
        lineTo(cx - r * 0.38f, cy + r * 0.35f)
        close()
    }
    drawPath(tower, bronze)

    // 1st Gallery bar
    drawRoundRect(
        color = bronze,
        topLeft = Offset(cx - r * 0.42f, cy + r * 0.28f),
        size = Size(r * 0.84f, r * 0.08f),
        cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
    )

    // 2nd Tier
    val midTier = Path().apply {
        moveTo(cx - r * 0.28f, cy + r * 0.26f)
        lineTo(cx + r * 0.28f, cy + r * 0.26f)
        lineTo(cx + r * 0.18f, cy - r * 0.12f)
        lineTo(cx - r * 0.18f, cy - r * 0.12f)
        close()
    }
    drawPath(midTier, bronze)

    // 2nd Gallery bar
    drawRoundRect(
        color = bronze,
        topLeft = Offset(cx - r * 0.22f, cy - r * 0.16f),
        size = Size(r * 0.44f, r * 0.06f),
        cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
    )

    // Upper Spire
    val spire = Path().apply {
        moveTo(cx - r * 0.12f, cy - r * 0.16f)
        lineTo(cx + r * 0.12f, cy - r * 0.16f)
        lineTo(cx + r * 0.02f, cy - r * 0.85f)
        lineTo(cx - r * 0.02f, cy - r * 0.85f)
        close()
    }
    drawPath(spire, bronze)

    // Pinnacle light beacon
    drawCircle(Color(0xFFFEF08A), r * 0.06f, Offset(cx, cy - r * 0.86f))
}

private fun DrawScope.drawPyramidsLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val sky = Color(0xFFFEF3C7)
    val goldLit = Color(0xFFFBBF24)
    val goldShadow = Color(0xFFD97706)
    val deepShadow = Color(0xFF92400E)
    val r = w * 0.38f

    // Desert Sun
    drawCircle(Color(0xFFF59E0B), r * 0.35f, Offset(cx + r * 0.5f, cy - r * 0.45f))

    // Great Pyramid (Center & Tallest)
    val pLit = Path().apply {
        moveTo(cx, cy - r * 0.55f)
        lineTo(cx + r * 0.55f, cy + r * 0.45f)
        lineTo(cx + r * 0.08f, cy + r * 0.48f)
        close()
    }
    drawPath(pLit, goldLit)

    val pShadow = Path().apply {
        moveTo(cx, cy - r * 0.55f)
        lineTo(cx + r * 0.08f, cy + r * 0.48f)
        lineTo(cx - r * 0.55f, cy + r * 0.45f)
        close()
    }
    drawPath(pShadow, goldShadow)

    // Left Secondary Pyramid
    val p2Lit = Path().apply {
        moveTo(cx - r * 0.45f, cy - r * 0.1f)
        lineTo(cx - r * 0.15f, cy + r * 0.55f)
        lineTo(cx - r * 0.4f, cy + r * 0.56f)
        close()
    }
    drawPath(p2Lit, goldLit)

    val p2Shadow = Path().apply {
        moveTo(cx - r * 0.45f, cy - r * 0.1f)
        lineTo(cx - r * 0.4f, cy + r * 0.56f)
        lineTo(cx - r * 0.85f, cy + r * 0.54f)
        close()
    }
    drawPath(p2Shadow, deepShadow)

    // Desert dunes floor
    val dune = Path().apply {
        moveTo(cx - r, cy + r * 0.45f)
        quadraticTo(cx - r * 0.2f, cy + r * 0.38f, cx + r * 0.3f, cy + r * 0.52f)
        quadraticTo(cx + r * 0.7f, cy + r * 0.62f, cx + r, cy + r * 0.48f)
        lineTo(cx + r, cy + r)
        lineTo(cx - r, cy + r)
        close()
    }
    drawPath(dune, Color(0xFFB45309))
}

private fun DrawScope.drawLibertyLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val seaGreen = Color(0xFF0D9488)
    val lightGreen = Color(0xFF2DD4BF)
    val gold = Color(0xFFF59E0B)
    val r = w * 0.38f

    // Soft sky circle
    drawCircle(Color(0xFFE0F2FE), r, Offset(cx, cy))

    // Torch Flame (Top Right)
    val flame = Path().apply {
        moveTo(cx + r * 0.45f, cy - r * 0.85f)
        quadraticTo(cx + r * 0.6f, cy - r * 0.5f, cx + r * 0.45f, cy - r * 0.35f)
        quadraticTo(cx + r * 0.3f, cy - r * 0.5f, cx + r * 0.45f, cy - r * 0.85f)
        close()
    }
    drawPath(flame, gold)
    // Torch arm/handle
    drawRect(
        color = seaGreen,
        topLeft = Offset(cx + r * 0.42f, cy - r * 0.35f),
        size = Size(r * 0.08f, r * 0.45f)
    )

    // Lady Liberty Profile & Crown
    // Head base
    drawCircle(seaGreen, r * 0.35f, Offset(cx - r * 0.15f, cy + r * 0.05f))

    // 7 Radiant Crown Spikes
    val crownBaseY = cy - r * 0.22f
    for (i in -3..3) {
        val spikeAngle = Math.toRadians((270 + i * 18).toDouble())
        val spikeTipX = (cx - r * 0.15f + Math.cos(spikeAngle) * r * 0.62f).toFloat()
        val spikeTipY = (cy - r * 0.15f + Math.sin(spikeAngle) * r * 0.62f).toFloat()
        val spike = Path().apply {
            moveTo(cx - r * 0.25f + i * r * 0.08f, crownBaseY)
            lineTo(spikeTipX, spikeTipY)
            lineTo(cx - r * 0.05f + i * r * 0.08f, crownBaseY)
            close()
        }
        drawPath(spike, lightGreen)
    }

    // Robe shoulder drapery
    val robe = Path().apply {
        moveTo(cx - r * 0.8f, cy + r * 0.9f)
        quadraticTo(cx - r * 0.4f, cy + r * 0.35f, cx, cy + r * 0.55f)
        lineTo(cx + r * 0.55f, cy + r * 0.9f)
        close()
    }
    drawPath(robe, seaGreen)
}

private fun DrawScope.drawColosseumLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val stoneGold = Color(0xFFD97706)
    val stoneDark = Color(0xFF78350F)
    val r = w * 0.38f

    // Warm background circle
    drawCircle(Color(0xFFFEF3C7), r, Offset(cx, cy))

    // Colosseum tiered wall silhouette
    val colosseum = Path().apply {
        moveTo(cx - r * 0.82f, cy + r * 0.55f)
        lineTo(cx - r * 0.82f, cy - r * 0.15f)
        // Outer grand wall steps down on the right (classic broken profile)
        lineTo(cx - r * 0.3f, cy - r * 0.38f)
        lineTo(cx + r * 0.15f, cy - r * 0.38f)
        lineTo(cx + r * 0.45f, cy - r * 0.05f)
        lineTo(cx + r * 0.82f, cy + r * 0.2f)
        lineTo(cx + r * 0.82f, cy + r * 0.55f)
        close()
    }
    drawPath(colosseum, stoneGold)

    // Tier 1 Arches (Bottom row)
    for (i in -3..3) {
        val archX = cx + i * r * 0.2f
        drawRoundRect(
            color = stoneDark,
            topLeft = Offset(archX - r * 0.06f, cy + r * 0.18f),
            size = Size(r * 0.12f, r * 0.28f),
            cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
        )
    }

    // Tier 2 Arches (Middle row)
    for (i in -3..1) {
        val archX = cx + i * r * 0.2f
        drawRoundRect(
            color = stoneDark,
            topLeft = Offset(archX - r * 0.05f, cy - r * 0.12f),
            size = Size(r * 0.1f, r * 0.22f),
            cornerRadius = CornerRadius(5.dp.toPx(), 5.dp.toPx())
        )
    }

    // Tier 3 Windows (Top row)
    for (i in -2..0) {
        val winX = cx + i * r * 0.2f
        drawRoundRect(
            color = stoneDark,
            topLeft = Offset(winX - r * 0.04f, cy - r * 0.32f),
            size = Size(r * 0.08f, r * 0.12f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )
    }
}

private fun DrawScope.drawTajMahalLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val midnight = Color(0xFF0F172A)
    val marbleWhite = Color(0xFFF8FAFC)
    val shadow = Color(0xFFE2E8F0)
    val r = w * 0.38f

    // Midnight blue backdrop
    drawCircle(midnight, r, Offset(cx, cy))

    // Base podium platform
    drawRoundRect(
        color = marbleWhite,
        topLeft = Offset(cx - r * 0.85f, cy + r * 0.55f),
        size = Size(r * 1.7f, r * 0.15f),
        cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
    )

    // 4 Corner Minarets (outer two taller, inner two perspective)
    val minaretLeft = Path().apply {
        moveTo(cx - r * 0.78f, cy + r * 0.55f)
        lineTo(cx - r * 0.72f, cy - r * 0.45f)
        lineTo(cx - r * 0.75f, cy - r * 0.52f)
        lineTo(cx - r * 0.78f, cy - r * 0.45f)
        close()
    }
    drawPath(minaretLeft, shadow)

    val minaretRight = Path().apply {
        moveTo(cx + r * 0.78f, cy + r * 0.55f)
        lineTo(cx + r * 0.72f, cy - r * 0.45f)
        lineTo(cx + r * 0.75f, cy - r * 0.52f)
        lineTo(cx + r * 0.78f, cy - r * 0.45f)
        close()
    }
    drawPath(minaretRight, shadow)

    // Main Central Building cube
    drawRect(
        color = marbleWhite,
        topLeft = Offset(cx - r * 0.48f, cy - r * 0.05f),
        size = Size(r * 0.96f, r * 0.6f)
    )

    // Central Grand Arched Iwan Portal
    val iwan = Path().apply {
        moveTo(cx - r * 0.22f, cy + r * 0.55f)
        lineTo(cx - r * 0.22f, cy + r * 0.15f)
        quadraticTo(cx, cy - r * 0.02f, cx + r * 0.22f, cy + r * 0.15f)
        lineTo(cx + r * 0.22f, cy + r * 0.55f)
        close()
    }
    drawPath(iwan, midnight)

    // Grand Onion Dome
    val dome = Path().apply {
        moveTo(cx - r * 0.32f, cy - r * 0.05f)
        cubicTo(cx - r * 0.42f, cy - r * 0.42f, cx - r * 0.08f, cy - r * 0.68f, cx, cy - r * 0.75f)
        cubicTo(cx + r * 0.08f, cy - r * 0.68f, cx + r * 0.42f, cy - r * 0.42f, cx + r * 0.32f, cy - r * 0.05f)
        close()
    }
    drawPath(dome, marbleWhite)

    // Spire on top of dome
    drawLine(Color(0xFFF59E0B), Offset(cx, cy - r * 0.75f), Offset(cx, cy - r * 0.88f), strokeWidth = 2.dp.toPx())
}

private fun DrawScope.drawBigBenLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val stone = Color(0xFF64748B)
    val clockFace = Color(0xFFFEF9C3)
    val dark = Color(0xFF1E293B)
    val gold = Color(0xFFF59E0B)
    val r = w * 0.38f

    // Soft London sky
    drawCircle(Color(0xFFE2E8F0), r, Offset(cx, cy))

    // Tower Shaft
    drawRect(
        color = stone,
        topLeft = Offset(cx - r * 0.38f, cy - r * 0.15f),
        size = Size(r * 0.76f, r * 1.05f)
    )

    // Belfry & Upper Spire
    val roof = Path().apply {
        moveTo(cx - r * 0.42f, cy - r * 0.45f)
        lineTo(cx, cy - r * 0.95f)
        lineTo(cx + r * 0.42f, cy - r * 0.45f)
        close()
    }
    drawPath(roof, stone)

    // Clock tier box
    drawRect(
        color = Color(0xFF475569),
        topLeft = Offset(cx - r * 0.4f, cy - r * 0.45f),
        size = Size(r * 0.8f, r * 0.35f)
    )

    // Circular Glowing Clock Face
    drawCircle(clockFace, r * 0.22f, Offset(cx, cy - r * 0.28f))
    drawCircle(dark, r * 0.22f, Offset(cx, cy - r * 0.28f), style = Stroke(width = 2.dp.toPx()))

    // Clock Hands (at 10 past 10)
    drawLine(dark, Offset(cx, cy - r * 0.28f), Offset(cx - r * 0.08f, cy - r * 0.38f), strokeWidth = 2.5f.dp.toPx())
    drawLine(dark, Offset(cx, cy - r * 0.28f), Offset(cx + r * 0.14f, cy - r * 0.34f), strokeWidth = 2.5f.dp.toPx())
    drawCircle(gold, r * 0.03f, Offset(cx, cy - r * 0.28f))
}

private fun DrawScope.drawFujiLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val redSun = Color(0xFFDC2626)
    val mountainIndigo = Color(0xFF1E1B4B)
    val snowWhite = Color(0xFFF8FAFC)
    val r = w * 0.38f

    // Rising Red Sun disc behind the peak
    drawCircle(redSun, r * 0.42f, Offset(cx, cy - r * 0.25f))

    // Mount Fuji cone
    val cone = Path().apply {
        moveTo(cx - r * 0.95f, cy + r * 0.85f)
        quadraticTo(cx - r * 0.45f, cy + r * 0.35f, cx - r * 0.22f, cy - r * 0.32f)
        lineTo(cx + r * 0.22f, cy - r * 0.32f)
        quadraticTo(cx + r * 0.45f, cy + r * 0.35f, cx + r * 0.95f, cy + r * 0.85f)
        close()
    }
    drawPath(cone, mountainIndigo)

    // Zigzag pristine snow cap
    val snow = Path().apply {
        moveTo(cx - r * 0.22f, cy - r * 0.32f)
        lineTo(cx + r * 0.22f, cy - r * 0.32f)
        lineTo(cx + r * 0.34f, cy + r * 0.05f)
        lineTo(cx + r * 0.18f, cy - r * 0.02f)
        lineTo(cx + r * 0.08f, cy + r * 0.12f)
        lineTo(cx, cy - r * 0.05f)
        lineTo(cx - r * 0.1f, cy + r * 0.08f)
        lineTo(cx - r * 0.22f, cy - r * 0.02f)
        lineTo(cx - r * 0.34f, cy + r * 0.05f)
        close()
    }
    drawPath(snow, snowWhite)
}

private fun DrawScope.drawPisaLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val marble = Color(0xFFF8FAFC)
    val dark = Color(0xFF334155)
    val grass = Color(0xFF16A34A)
    val r = w * 0.38f

    // Soft sky
    drawCircle(Color(0xFFE0F2FE), r, Offset(cx, cy))

    // Green Field base
    drawRect(
        color = grass,
        topLeft = Offset(cx - r, cy + r * 0.65f),
        size = Size(r * 2f, r * 0.35f)
    )

    // Leaning Tower (tilted ~8 degrees to the right)
    drawContext.canvas.save()
    drawContext.transform.rotate(8f, Offset(cx, cy + r * 0.6f))

    val towerW = r * 0.45f
    val towerH = r * 1.35f
    val startY = cy - r * 0.7f

    // Base cylinder
    drawRect(
        color = marble,
        topLeft = Offset(cx - towerW / 2f, startY),
        size = Size(towerW, towerH)
    )

    // 6 Levels of Arcaded Galleries
    val tierH = towerH / 7.5f
    for (t in 1..6) {
        val tierY = startY + t * tierH
        // Balcony ledge
        drawRect(
            color = Color(0xFFE2E8F0),
            topLeft = Offset(cx - towerW * 0.55f, tierY),
            size = Size(towerW * 1.1f, 3.dp.toPx())
        )
        // Arches across gallery
        for (a in -2..2) {
            val archX = cx + a * (towerW / 5f)
            drawRoundRect(
                color = dark,
                topLeft = Offset(archX - 2.dp.toPx(), tierY + 4.dp.toPx()),
                size = Size(4.dp.toPx(), tierH * 0.65f),
                cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
            )
        }
    }

    // Top Belfry
    drawRect(
        color = marble,
        topLeft = Offset(cx - towerW * 0.38f, startY - tierH * 0.8f),
        size = Size(towerW * 0.76f, tierH * 0.8f)
    )

    drawContext.canvas.restore()
}

private fun DrawScope.drawSphinxLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val gold = Color(0xFFD97706)
    val darkGold = Color(0xFFB45309)
    val r = w * 0.38f

    // Warm desert sunset sky
    drawCircle(Color(0xFFFEF3C7), r, Offset(cx, cy))

    // Recumbent Lion Body
    val body = Path().apply {
        moveTo(cx - r * 0.85f, cy + r * 0.55f)
        quadraticTo(cx - r * 0.75f, cy + r * 0.15f, cx - r * 0.35f, cy + r * 0.25f)
        lineTo(cx + r * 0.1f, cy + r * 0.25f)
        // Back flank
        quadraticTo(cx + r * 0.65f, cy + r * 0.15f, cx + r * 0.85f, cy + r * 0.55f)
        close()
    }
    drawPath(body, gold)

    // Outstretched Front Paws
    drawRoundRect(
        color = darkGold,
        topLeft = Offset(cx - r * 0.95f, cy + r * 0.45f),
        size = Size(r * 0.45f, r * 0.15f),
        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
    )

    // Pharaoh Head with Flared Nemes Headdress
    val nemes = Path().apply {
        moveTo(cx - r * 0.35f, cy - r * 0.45f)
        lineTo(cx + r * 0.05f, cy - r * 0.45f)
        lineTo(cx + r * 0.18f, cy - r * 0.05f)
        lineTo(cx - r * 0.05f, cy + r * 0.25f)
        lineTo(cx - r * 0.25f, cy + r * 0.25f)
        lineTo(cx - r * 0.48f, cy - r * 0.05f)
        close()
    }
    drawPath(nemes, gold)

    // Nemes Stripes
    for (i in -3..1) {
        val yPos = cy - r * 0.35f + i * r * 0.12f
        drawLine(
            color = Color(0xFF1E3A8A),
            start = Offset(cx - r * 0.35f, yPos),
            end = Offset(cx + r * 0.05f, yPos),
            strokeWidth = 2.dp.toPx()
        )
    }

    // Pharaoh Face profile
    drawCircle(darkGold, r * 0.15f, Offset(cx - r * 0.15f, cy - r * 0.12f))
}

private fun DrawScope.drawSydneyLogo(cx: Float, cy: Float, w: Float, h: Float) {
    val harborBlue = Color(0xFF0369A1)
    val deepWater = Color(0xFF0C4A6E)
    val shellWhite = Color(0xFFF8FAFC)
    val shellShadow = Color(0xFFE2E8F0)
    val r = w * 0.38f

    // Harbor water base
    drawCircle(harborBlue, r, Offset(cx, cy))
    drawRect(
        color = deepWater,
        topLeft = Offset(cx - r, cy + r * 0.42f),
        size = Size(r * 2f, r * 0.58f)
    )

    // Sail Shell 1 (Large left)
    val shell1 = Path().apply {
        moveTo(cx - r * 0.75f, cy + r * 0.42f)
        quadraticTo(cx - r * 0.4f, cy + r * 0.1f, cx - r * 0.25f, cy - r * 0.52f)
        quadraticTo(cx, cy - r * 0.1f, cx + r * 0.15f, cy + r * 0.42f)
        close()
    }
    drawPath(shell1, shellShadow)
    val shell1Face = Path().apply {
        moveTo(cx - r * 0.75f, cy + r * 0.42f)
        quadraticTo(cx - r * 0.4f, cy + r * 0.1f, cx - r * 0.25f, cy - r * 0.52f)
        lineTo(cx - r * 0.18f, cy + r * 0.42f)
        close()
    }
    drawPath(shell1Face, shellWhite)

    // Sail Shell 2 (Center medium)
    val shell2 = Path().apply {
        moveTo(cx - r * 0.25f, cy + r * 0.42f)
        quadraticTo(cx + r * 0.05f, cy + r * 0.15f, cx + r * 0.18f, cy - r * 0.35f)
        quadraticTo(cx + r * 0.4f, cy - r * 0.05f, cx + r * 0.55f, cy + r * 0.42f)
        close()
    }
    drawPath(shell2, shellShadow)
    val shell2Face = Path().apply {
        moveTo(cx - r * 0.25f, cy + r * 0.42f)
        quadraticTo(cx + r * 0.05f, cy + r * 0.15f, cx + r * 0.18f, cy - r * 0.35f)
        lineTo(cx + r * 0.24f, cy + r * 0.42f)
        close()
    }
    drawPath(shell2Face, shellWhite)

    // Sail Shell 3 (Small right)
    val shell3 = Path().apply {
        moveTo(cx + r * 0.15f, cy + r * 0.42f)
        quadraticTo(cx + r * 0.4f, cy + r * 0.25f, cx + r * 0.5f, cy - r * 0.15f)
        quadraticTo(cx + r * 0.68f, cy + r * 0.05f, cx + r * 0.8f, cy + r * 0.42f)
        close()
    }
    drawPath(shell3, shellWhite)

    // Platform podium edge
    drawRoundRect(
        color = Color(0xFF94A3B8),
        topLeft = Offset(cx - r * 0.82f, cy + r * 0.4f),
        size = Size(r * 1.68f, r * 0.08f),
        cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
    )
}

