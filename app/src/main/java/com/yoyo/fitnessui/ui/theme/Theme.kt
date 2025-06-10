// Theme.kt
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color // Make sure Color is imported

// Use your newly defined colors here
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryYellow, // Your main accent color
    secondary = LightGrayishBlue, // A secondary accent color
    tertiary = Pink80, // You can keep or change this
    background = DarkBackground, // Your dark background color
    surface = DarkSurface,       // Your dark surface color
    onPrimary = Color.Black,     // Text/icons on primary color (e.g., on PrimaryYellow button)
    onSecondary = Color.White,   // Text/icons on secondary color
    onBackground = Color.White,  // Text/icons on background color
    onSurface = Color.White      // Text/icons on surface color
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
    // Define proper light theme colors if you intend to support a light theme
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun FitnessUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            // Use dynamic colors if available and enabled, otherwise fall back to our defined schemes
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme // Use our custom DarkColorScheme
        else -> LightColorScheme // Or your custom LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set status bar and navigation bar colors to transparent
            // This ensures content can be drawn behind them for the fullscreen effect
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            // Control the contrast of system bar icons (dark icons on light background, or vice versa)
            // If your background is dark, you want light icons (false).
            // If your background is light, you want dark icons (true).
            // Since our app background is dark, we want light icons.
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false // Icons will be light
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false // Icons will be light
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Your defined typography
        content = content
    )
}