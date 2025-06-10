package com.yoyo.fitnessui.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yoyo.fitnessui.ui.viewmodel.WorkoutViewModel

@Composable
fun WorkoutScreen(viewModel: WorkoutViewModel = viewModel()) {
    val workoutData by viewModel.workoutData.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        workoutData?.workouts?.forEach { day ->
            Text("Day ${day.day}", style = MaterialTheme.typography.bodySmall)
            day.workout.forEach { exercise ->
                Text("- ${exercise.exerciseName}")
            }
            Spacer(Modifier.height(16.dp))
        } ?: CircularProgressIndicator()
    }
}
