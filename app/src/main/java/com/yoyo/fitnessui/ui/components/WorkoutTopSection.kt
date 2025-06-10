package com.yoyo.fitnessui.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.yoyo.fitnessui.ui.theme.FitnessUITheme


/**
 * Composable for the top section of the workout screen, containing filter chips.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutTopSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterChip(text = "Muscles (16)", icon = Icons.Default.ArrowDropDown)
        FilterChip(text = "45-60 Min", icon = Icons.Default.ArrowDropDown)
        FilterChip(text = "Schedule", icon = Icons.Default.ArrowDropDown)
    }
}

/**
 * Composable for a filter chip (e.g., "Muscles (16)").
 * This was moved from MyWorkoutScreen and made reusable.
 *
 * @param text The text to display on the chip.
 * @param icon The leading icon for the chip.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChip(text: String, icon: ImageVector) {
    AssistChip(
        onClick = { /* TODO: Handle filter click */ },
        label = {
            Text(text = text, color = Color.White)
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null, // Content description for accessibility
                tint = Color.White
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = Color.Gray.copy(alpha = 0.2f) // Semi-transparent gray background
        ),
        border = null // No border
    )
}

@Preview(showBackground = true)
@Composable
fun WorkoutTopSectionPreview() {
    FitnessUITheme {
        WorkoutTopSection()
    }
}