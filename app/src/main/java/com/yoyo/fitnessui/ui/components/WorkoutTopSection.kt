package com.yoyo.fitnessui.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import com.yoyo.fitnessui.ui.theme.FitnessUITheme


/**
 * Composable for the top section of the workout screen, containing filter chips.
 *
 * @param onMusclesClick Lambda invoked when the "Muscles" filter is clicked.
 * @param onTimeClick Lambda invoked when the "45-60 Min" filter is clicked.
 * @param onScheduleClick Lambda invoked when the "Schedule" filter is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutTopSection(
    onMusclesClick: () -> Unit,
    onTimeClick: () -> Unit,
    onScheduleClick: () -> Unit
) {
    // Use LazyRow for horizontally scrollable filter chips
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp), // Spacing between chips
        verticalAlignment = Alignment.CenterVertically
    ) {
        item { // Wrap each chip in an item for LazyRow
            FilterChip(text = "Muscles (16)", icon = Icons.Default.ArrowDropDown, onClick = onMusclesClick)
        }
        item {
            FilterChip(text = "45-60 Min", icon = Icons.Default.ArrowDropDown, onClick = onTimeClick)
        }
        item {
            FilterChip(text = "Schedule", icon = Icons.Default.ArrowDropDown, onClick = onScheduleClick)
        }
        // If more chips are added, they will automatically scroll
    }
}

/**
 * Composable for a filter chip (e.g., "Muscles (16)").
 *
 * @param text The text to display on the chip.
 * @param icon The leading icon for the chip.
 * @param onClick Lambda invoked when the chip is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChip(text: String, icon: ImageVector, onClick: () -> Unit) {
    AssistChip(
        onClick = onClick,
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
            containerColor = Color.Gray.copy(alpha = 0.2f)
        ),
        border = null
    )
}

@Preview(showBackground = true)
@Composable
fun WorkoutTopSectionPreview() {
    FitnessUITheme {
        WorkoutTopSection(onMusclesClick = {}, onTimeClick = {}, onScheduleClick = {})
    }
}