package com.yoyo.fitnessui.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Defines the dark color scheme for the application.
 * Uses custom [PrimaryYellow] for primary elements and [DarkBackground] for overall background.
 */
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryYellow, // Main accent color (e.g., buttons, selected states)
    secondary = LightGrayishBlue, // Secondary elements color
    tertiary = Pink80, // Another accent color (optional)
    background = DarkBackground, // General background color for the screen
    surface = DarkBackground, // Background color for surfaces like Cards
    onPrimary = Color.Black, // Text/icon color on primary background
    onSecondary = Color.White, // Text/icon color on secondary background
    onTertiary = Color.White, // Text/icon color on tertiary background
    onBackground = Color.White, // Text/icon color on general background
    onSurface = Color.White // Text/icon color on surface background
)

/**
 * Defines the light color scheme for the application.
 * Retains [PrimaryYellow] for consistency but uses white for background.
 */
private val LightColorScheme = lightColorScheme(
    primary = PrimaryYellow,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color.White, // Light background for the screen
    surface = Color.White, // Light background for surfaces
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

/**
 * Composable function to apply the application's theme.
 * It selects between dark and light themes, and supports dynamic colors on Android 12+.
 *
 * @param darkTheme Whether the dark theme should be applied. Defaults to system setting.
 * @param dynamicColor Whether dynamic colors should be used if available (Android 12+).
 * @param content The composable content to be themed.
 */
@Composable
fun FitnessUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Determine the color scheme to use based on system theme and dynamic color preference.
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Set the status bar color to match the app's background.
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            // Adjust status bar icons color based on theme background lightness
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    // Apply the chosen Material Theme to the content.
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Uses typography defined in Type.kt
        content = content
    )
}