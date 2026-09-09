package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = TailwindBlueLight,
    onPrimary = Slate900,
    primaryContainer = TailwindBlueDark,
    onPrimaryContainer = CleanWhite,
    secondary = CyanAccent,
    onSecondary = Slate900,
    background = Slate900,
    surface = Slate700,
    onBackground = CleanWhite,
    onSurface = CleanWhite,
  )

private val LightColorScheme =
  lightColorScheme(
    primary = TailwindBlue,
    onPrimary = CleanWhite,
    primaryContainer = TailwindBlueSurface,
    onPrimaryContainer = TailwindBlueDark,
    secondary = IndigoPrimary,
    onSecondary = CleanWhite,
    background = Slate50,
    surface = CleanWhite,
    onBackground = CharcoalDark,
    onSurface = CharcoalDark,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate700,
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

