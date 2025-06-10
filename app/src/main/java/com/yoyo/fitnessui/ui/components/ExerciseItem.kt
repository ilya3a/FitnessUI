package com.yoyo.fitnessui.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.yoyo.fitnessui.R
import com.yoyo.fitnessui.data.model.Exercise
import com.yoyo.fitnessui.ui.theme.DarkBackground
import com.yoyo.fitnessui.ui.theme.LightGrayishBlue

/**
 * Composable function for a single exercise item displayed in the workout list.
 * It shows the exercise thumbnail, name, sets/reps/weight, and muscle group image.
 *
 * @param exercise The [Exercise] data object to display.
 */
@Composable
fun ExerciseItem(exercise: Exercise) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkBackground), // Dark background for the card
        shape = RoundedCornerShape(8.dp) // Rounded corners for the card
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Exercise thumbnail image loaded with Coil from assets
            AsyncImage(
                model = "file:///android_asset/${exercise.exerciseThumbnail}", // Load from assets
                contentDescription = exercise.exerciseName,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                // Placeholder image in case the asset is not found
                error = painterResource(id = R.drawable.ic_launcher_background) // Replace with a generic workout image asset
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.exerciseName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Format the text for sets, reps, and optional weight
                val weightText = exercise.weightAmount?.let { " x $it lb" } ?: ""
                Text(
                    text = "${exercise.amountOfSets} sets x ${exercise.repRange} reps$weightText",
                    color = LightGrayishBlue,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Muscle group image loaded with Coil from assets
            AsyncImage(
                model = "file:///android_asset/${exercise.muscleGroupImage}", // Load from assets
                contentDescription = exercise.muscleGroup,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit,
                // Placeholder image in case the asset is not found
                error = painterResource(id = R.drawable.ic_launcher_background) // Replace with a generic muscle group image asset
            )
        }
    }
}



