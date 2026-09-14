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
    primary = WireTeal,
    onPrimary = CleanWhite,
    primaryContainer = Color(0xFF2B2622),
    onPrimaryContainer = Color(0xFFEDE5DD),
    secondary = WireAmber,
    onSecondary = CleanWhite,
    background = Color(0xFF161311),
    surface = Color(0xFF221E1B),
    onBackground = Color(0xFFEDE5DD),
    onSurface = Color(0xFFEDE5DD),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = WireTeal,
    onPrimary = CleanWhite,
    primaryContainer = WireTealDim,
    onPrimaryContainer = WireTeal,
    secondary = WireAmber,
    onSecondary = CleanWhite,
    background = WarmBg,
    surface = WarmSurface,
    onBackground = WarmText,
    onSurface = WarmText,
    surfaceVariant = WarmSurface2,
    onSurfaceVariant = WarmTextDim,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Use intentional game palette by default
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

