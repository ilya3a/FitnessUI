package com.yoyo.fitnessui.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yoyo.fitnessui.ui.viewmodel.WorkoutViewModel
import com.yoyo.fitnessui.ui.components.DaySelector
import com.yoyo.fitnessui.ui.components.ExerciseItem
import com.yoyo.fitnessui.ui.components.BottomNavigationBar
import com.yoyo.fitnessui.ui.components.WorkoutTopSection
import com.yoyo.fitnessui.ui.components.WorkoutSummarySection
import com.yoyo.fitnessui.ui.components.StartWorkoutButton
import com.yoyo.fitnessui.ui.theme.FitnessUITheme
import com.yoyo.fitnessui.ui.theme.DarkBackground
import com.yoyo.fitnessui.ui.theme.LightGrayishBlue


/**
 * The main composable screen for "My Workout".
 * It orchestrates the various UI components to display workout details.
 *
 * @param viewModel The [WorkoutViewModel] providing workout data and state.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWorkoutScreen(viewModel: WorkoutViewModel) {
    // Collect state from ViewModel to trigger UI recomposition on data changes
    val currentWorkoutDay by viewModel.currentWorkoutDay.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Workout",
                        color = Color.White,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DarkBackground
                )
            )
        },
        containerColor = DarkBackground,
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Top section with filter chips
            WorkoutTopSection() // Extracted component

            Spacer(modifier = Modifier.height(16.dp))

            // Day selector component
            DaySelector(selectedDay = selectedDay, onDaySelected = { viewModel.selectDay(it) })

            Spacer(modifier = Modifier.height(16.dp))

            // Workout summary section (Upcoming workout header, muscle group, edit icon, summary items)
            WorkoutSummarySection(currentWorkoutDay = currentWorkoutDay) // Extracted component

            Spacer(modifier = Modifier.height(16.dp))

            // LazyColumn to display the list of exercises
            currentWorkoutDay?.let { workoutDay ->
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(workoutDay.workout) { exercise ->
                        ExerciseItem(exercise = exercise)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } ?: run {
                Text(
                    text = "No workout planned for this day.",
                    color = LightGrayishBlue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Start Workout Button
            StartWorkoutButton(onClick = { /* TODO: Handle start workout action */ }) // Extracted component

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

/**
 * Preview composable for [MyWorkoutScreen].
 * Note: Full preview with ViewModel is complex. Focus on running on emulator.
 */
@Preview(showBackground = true)
@Composable
fun MyWorkoutScreenPreview() {
    FitnessUITheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = DarkBackground
        ) {
            Text("Screen Preview Not Available without Mock ViewModel", color = Color.White, modifier = Modifier.wrapContentSize(androidx.compose.ui.Alignment.Center))
        }
    }
}