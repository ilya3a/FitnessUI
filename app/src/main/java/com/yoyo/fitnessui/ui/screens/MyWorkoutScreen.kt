// MyWorkoutScreen.kt
package com.yoyo.fitnessui.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yoyo.fitnessui.ui.components.BottomNavigationBar
import com.yoyo.fitnessui.ui.components.DaySelector
import com.yoyo.fitnessui.ui.components.ExerciseItem
import com.yoyo.fitnessui.ui.components.StartWorkoutButton
import com.yoyo.fitnessui.ui.components.WorkoutSummarySection
import com.yoyo.fitnessui.ui.components.WorkoutTopSection
import com.yoyo.fitnessui.ui.theme.DarkBackground
import com.yoyo.fitnessui.ui.theme.FitnessUITheme
import com.yoyo.fitnessui.ui.theme.LightGrayishBlue
import com.yoyo.fitnessui.ui.viewmodel.WorkoutViewModel

/**
 * The main workout screen composable.
 * Displays workout details, day selector, exercise list, and a start workout button.
 *
 * @param viewModel The [WorkoutViewModel] instance providing data and handling logic.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWorkoutScreen(viewModel: WorkoutViewModel = viewModel()) {
    // Observe the current workout day from the ViewModel's StateFlow.
    val currentWorkoutDay by viewModel.currentWorkoutDay.collectAsState()
    // Observe the currently selected day number.
    val selectedDay by viewModel.selectedDay.collectAsState()
    // Observe the map of completion statuses for all days.
    val daysCompletionStatus by viewModel.daysCompletionStatus.collectAsState()
    // Observe the total number of days from the ViewModel.
    val totalDays by viewModel.totalDays.collectAsState() // This should now correctly reflect JSON size

    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(
            onMyWorkoutClick = { /* Handle my workout click */ },
            onExerciseClick = { /* Handle exercise click */ },
            onProgressClick = { /* Handle progress click */ },
            onSettingsClick = { /* Handle settings click */ }
        ) } // Assuming you have this component
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
                .padding(paddingValues) // Apply padding from Scaffold to avoid content being hidden by bottom bar
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                // Top section with filter chips
                WorkoutTopSection(
                    onMusclesClick = { /* Handle muscles filter click */ },
                    onTimeClick = { /* Handle time filter click */ },
                    onScheduleClick = { /* Handle schedule filter click */ }
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Day Selector chips
                DaySelector(
                    selectedDay = selectedDay,
                    onDaySelected = { day ->
                        viewModel.selectDay(day)
                    },
                    daysCompletionStatus = daysCompletionStatus, // Pass the map of day completion statuses
                    totalDays = totalDays // Pass the total number of days from ViewModel
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Workout Summary Section
                WorkoutSummarySection(
                    currentWorkoutDay = currentWorkoutDay,
                    onEditClick = {
                        Toast.makeText(context, "Edit/Share clicked!", Toast.LENGTH_SHORT).show()
                    },
                    // Get the completion status for the current selected day from the map
                    isDayCompleted = daysCompletionStatus[selectedDay] ?: false
                )
                Spacer(modifier = Modifier.height(16.dp))

                // List of exercises for the current day
                currentWorkoutDay?.let { workoutDay ->
                    if (workoutDay.workout.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f), // Take remaining height, allowing the column to scroll
                            contentPadding = PaddingValues(bottom = 80.dp), // Add padding for the FAB at the bottom
                            verticalArrangement = Arrangement.spacedBy(8.dp) // Spacing between exercise items
                        ) {
                            items(workoutDay.workout) { exercise ->
                                ExerciseItem(
                                    exercise = exercise,
                                    onToggleComplete = {
                                        viewModel.toggleExerciseCompletion(workoutDay.day, exercise.exerciseId)
                                    }
                                )
                            }
                        }
                    } else {
                        // Display message if no workout is planned for the selected day
                        Text(
                            text = "No workout planned for this day.",
                            color = LightGrayishBlue,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Start Workout Button - positioned at the bottom center of the Box
            StartWorkoutButton(
                onClick = { Toast.makeText(context, "START WORKOUT clicked!", Toast.LENGTH_SHORT).show() },
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Align to the bottom center of the parent Box
                    .padding(bottom = 12.dp) // Add padding from the bottom edge
            )
        }
    }
}

/**
 * Preview composable for [MyWorkoutScreen].
 * Note: A full preview with a real ViewModel is complex. For a realistic preview,
 * run on an emulator or device. This preview provides a basic placeholder.
 */
@Preview(showBackground = true)
@Composable
fun MyWorkoutScreenPreview() {
    FitnessUITheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkBackground
        ) {
            // Display a message indicating that a full preview requires a ViewModel
            Text(
                "Screen Preview Not Available without Mock ViewModel",
                color = Color.White,
                modifier = Modifier.wrapContentSize(Alignment.Center)
            )
        }
    }
}