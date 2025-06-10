package com.yoyo.fitnessui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel // Import for ViewModel in Compose
import com.yoyo.fitnessui.ui.screens.MyWorkoutScreen // Import our main workout screen
import com.yoyo.fitnessui.ui.theme.FitnessUITheme // Import our custom theme
import com.yoyo.fitnessui.ui.viewmodel.WorkoutViewModel // Import our ViewModel

/**
 * The main activity of the application.
 * This is the entry point for the UI using Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    // In a larger app, this is where a NavHost would typically be defined for navigation.
    // For this assignment, we directly show the MyWorkoutScreen.
    MyWorkoutScreen(viewModel = viewModel)
}