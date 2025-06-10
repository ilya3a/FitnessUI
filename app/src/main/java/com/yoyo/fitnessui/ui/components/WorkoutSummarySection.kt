package com.yoyo.fitnessui.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoyo.fitnessui.data.model.WorkoutDay
import com.yoyo.fitnessui.ui.theme.FitnessUITheme
import com.yoyo.fitnessui.ui.theme.LightGrayishBlue

/**
 * Composable for the workout summary section.
 * Includes "UPCOMING WORKOUT" header, muscle group, edit/share icon, and summary items.
 *
 * @param currentWorkoutDay The [WorkoutDay] object containing current workout details, can be null.
 * @param onEditClick Lambda invoked when the edit/share icon is clicked.
 * @param isDayCompleted Boolean indicating if the currently selected day is completed.
 */
@Composable
fun WorkoutSummarySection(
    currentWorkoutDay: WorkoutDay?,
    onEditClick: () -> Unit,
    isDayCompleted: Boolean // Added isDayCompleted
) {
    // UPCOMING WORKOUT Section header
    Text(
        text = "UPCOMING WORKOUT",
        color = LightGrayishBlue,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Display the muscle group of the first exercise for the selected day, or a default
        Text(
            text = currentWorkoutDay?.workout?.firstOrNull()?.muscleGroup ?: "Full Body",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        // Edit/Share workout icon button based on day completion
        IconButton(onClick = onEditClick) {
            val icon = if (isDayCompleted) Icons.Default.Share else Icons.Default.Edit // Choose icon
            val contentDescription = if (isDayCompleted) "Share Workout" else "Edit Workout"
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = LightGrayishBlue
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Workout Summary section (Exercises, Min, Cal)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WorkoutSummaryItem(
            value = "${currentWorkoutDay?.workout?.size ?: 0}",
            label = "Exercises"
        )
        WorkoutSummaryItem(value = "53", label = "Min")
        WorkoutSummaryItem(value = "265", label = "Cal")
    }
}

/**
 * Composable for displaying a single summary item (e.g., "6 Exercises").
 *
 * @param value The numerical value or main text.
 * @param label The descriptive label below the value.
 */
@Composable
fun WorkoutSummaryItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = label, color = LightGrayishBlue, fontSize = 12.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutSummarySectionPreview() {
    FitnessUITheme {
        val mockWorkoutDay = com.yoyo.fitnessui.data.model.WorkoutDay(
            day = 1,
            workout = listOf(
                com.yoyo.fitnessui.data.model.Exercise(1, "Mock Exercise 1", "exc_t_159_ronals.jpg", "Chest", "Muscle Groups 1.png", 3, "8-10", "100")
            )
        )
        Column {
            WorkoutSummarySection(currentWorkoutDay = mockWorkoutDay, onEditClick = {}, isDayCompleted = false)
            Spacer(modifier = Modifier.height(16.dp))
            WorkoutSummarySection(currentWorkoutDay = mockWorkoutDay, onEditClick = {}, isDayCompleted = true) // Example: Day completed
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutSummarySectionEmptyPreview() {
    FitnessUITheme {
        WorkoutSummarySection(currentWorkoutDay = null, onEditClick = {}, isDayCompleted = false)
    }
}