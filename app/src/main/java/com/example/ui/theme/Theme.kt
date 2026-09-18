package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
    darkColorScheme(
        primary = ArcadeFlame,
        onPrimary = CleanWhite,
        primaryContainer = ArcadeCardElevated,
        onPrimaryContainer = CleanWhite,
        secondary = ArcadeNeonGreen,
        onSecondary = ArcadeCanvas,
        background = ArcadeBg,
        surface = ArcadeCard,
        onBackground = ArcadeText,
        onSurface = ArcadeText,
        surfaceVariant = ArcadeCardSecondary,
        onSurfaceVariant = ArcadeTextDim
    )

private val LightColorScheme = DarkColorScheme // Unified Dark Arcade aesthetic matching design artifact

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false, // Preserve intentional dark arcade palette
    content: @Composable () -> Unit,
) {
    val colorScheme = DarkColorScheme
    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}


