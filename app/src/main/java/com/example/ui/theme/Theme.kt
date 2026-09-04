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
    primary = PolishIceBlue,
    onPrimary = PolishNavyDark,
    primaryContainer = PolishNavyDark,
    onPrimaryContainer = PolishIceBlue,
    secondary = PolishPurpleBar,
    onSecondary = Color.White,
    secondaryContainer = PolishPurpleDark,
    onSecondaryContainer = PolishPurpleLight,
    tertiary = SuccessEmerald,
    background = Color(0xFF10131A),
    surface = Color(0xFF1A1C24),
    surfaceVariant = Color(0xFF242833),
    onBackground = PolishBackground,
    onSurface = PolishBackground,
    onSurfaceVariant = PolishBorderLight,
    outline = PolishBorder
  )

private val LightColorScheme =
  lightColorScheme(
    primary = PolishBluePrimary,
    onPrimary = Color.White,
    primaryContainer = PolishIceBlue,
    onPrimaryContainer = PolishNavyDark,
    secondary = PolishPurpleBar,
    onSecondary = Color.White,
    secondaryContainer = PolishPurpleLight,
    onSecondaryContainer = PolishPurpleDark,
    tertiary = SuccessEmerald,
    background = PolishBackground,
    surface = Color.White,
    surfaceVariant = PolishNavBg,
    onBackground = PolishTextPrimary,
    onSurface = PolishTextPrimary,
    onSurfaceVariant = PolishTextSecondary,
    outline = PolishBorderLight
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Can support custom dynamic color if desired
  dynamicColor: Boolean = false,
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

