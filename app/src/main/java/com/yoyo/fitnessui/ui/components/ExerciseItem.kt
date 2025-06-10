// ExerciseItem.kt
package com.yoyo.fitnessui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.yoyo.fitnessui.R
import com.yoyo.fitnessui.data.model.Exercise
import com.yoyo.fitnessui.ui.theme.DarkBackground
import com.yoyo.fitnessui.ui.theme.FitnessUITheme
import com.yoyo.fitnessui.ui.theme.LightGrayishBlue
import com.yoyo.fitnessui.ui.theme.PrimaryYellow

/**
 * Composable for displaying a single exercise item in the workout list.
 *
 * @param exercise The [Exercise] data to display.
 * @param onToggleComplete Lambda function invoked when the exercise item is clicked to toggle its completion status.
 * This lambda now takes no arguments, as the ExerciseItem already knows which exercise it represents.
 */
@Composable
fun ExerciseItem(
    exercise: Exercise,
    onToggleComplete: () -> Unit // IMPORTANT: This now takes no arguments.
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleComplete() }, // Call without arguments
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray.copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Exercise thumbnail
            AsyncImage(
                model = "file:///android_asset/${exercise.exerciseThumbnail}",
                contentDescription = exercise.exerciseName,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_background) // Fallback image
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Exercise name and details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.exerciseName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                val weightText = exercise.weightAmount?.let { " x $it lb" } ?: ""
                Text(
                    text = "${exercise.amountOfSets} sets x ${exercise.repRange} reps$weightText",
                    color = LightGrayishBlue,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Muscle group image or completion indicator
            if (exercise.isCompleted) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Completed",
                    tint = PrimaryYellow, // Bright color for completion
                    modifier = Modifier.size(40.dp)
                )
            } else {
                AsyncImage(
                    model = "file:///android_asset/${exercise.muscleGroupImage}",
                    contentDescription = exercise.muscleGroup,
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit,
                    error = painterResource(id = R.drawable.ic_launcher_background) // Fallback image
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExerciseItemPreview() {
    FitnessUITheme {
        Column(modifier = Modifier.background(DarkBackground).padding(16.dp)) {
            val mockExercise = Exercise(
                exerciseId = 1,
                exerciseName = "Sample Exercise",
                exerciseThumbnail = "exc_t_159_ronals.jpg",
                muscleGroup = "Legs",
                muscleGroupImage = "Muscle Groups 1.png",
                amountOfSets = 3,
                repRange = "8-10",
                weightAmount = "100"
            )

            // Preview for an uncompleted exercise
            ExerciseItem(exercise = mockExercise, onToggleComplete = {})
            Spacer(modifier = Modifier.height(8.dp))

            // Preview for a completed exercise
            ExerciseItem(exercise = mockExercise.copy(isCompleted = true), onToggleComplete = {})
        }
    }
}