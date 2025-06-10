// MainActivity.kt
package com.yoyo.fitnessui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yoyo.fitnessui.ui.screens.MyWorkoutScreen
import com.yoyo.fitnessui.ui.theme.FitnessUITheme
import com.yoyo.fitnessui.ui.viewmodel.WorkoutViewModel
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat // NEW IMPORT

/**
 * The main activity of the application.
 * This is the entry point for the UI using Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure the app draws behind the system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Get the WindowInsetsControllerCompat to control system bars visibility
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        // Configure the behavior of the system bars
        // HIDE the system bars (status bar and navigation bar)
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        // OPTIONAL: Make the system bars (if shown, e.g., on swipe) translucent or not
        // This makes the navigation bar non-translucent after it appears if swiped
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContent {
            // Apply the custom theme to the entire application UI
            FitnessUITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background // Use the background color defined in our theme
                ) {
                    MyWorkoutApp()
                }
            }
        }
    }
}

/**
 * The root composable function for the application's UI.
 * It provides the [WorkoutViewModel] to the [MyWorkoutScreen].
 */
@Composable
fun MyWorkoutApp(viewModel: WorkoutViewModel = viewModel()) {
    MyWorkoutScreen(viewModel = viewModel)
}