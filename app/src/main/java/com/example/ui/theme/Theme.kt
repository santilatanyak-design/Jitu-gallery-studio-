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

private val DarkColorScheme = darkColorScheme(
    primary = StudioCrimsonLight,
    onPrimary = Color.White,
    primaryContainer = StudioNavyDark,
    onPrimaryContainer = Color.White,
    secondary = StudioGold,
    onSecondary = StudioNavyDark,
    tertiary = StudioTeal,
    background = DarkBackground,
    surface = DarkSurface,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = StudioCrimson,
    onPrimary = Color.White,
    primaryContainer = StudioNavy,
    onPrimaryContainer = Color.White,
    secondary = StudioNavy,
    onSecondary = Color.White,
    tertiary = StudioGold,
    background = LightBackground,
    surface = LightSurface,
    onBackground = TextPrimaryLight,
    onSurface = TextPrimaryLight
)

@Composable
fun JituGalleryStudioTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep signature consistent brand identity
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

