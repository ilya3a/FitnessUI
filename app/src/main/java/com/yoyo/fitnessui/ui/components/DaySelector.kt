package com.yoyo.fitnessui.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoyo.fitnessui.ui.theme.PrimaryYellow

/**
 * Composable function for selecting a workout day.
 * Displays a horizontal row of clickable day cards.
 *
 * @param selectedDay The currently selected day number.
 * @param onDaySelected Callback function invoked when a day card is clicked, providing the selected day number.
 */
@Composable
fun DaySelector(selectedDay: Int, onDaySelected: (Int) -> Unit) {
    // Assuming days 1, 2, 3, 4 are available based on the JSON structure.
    // In a real app, this list might come from the ViewModel.
    val days = listOf(1, 2, 3, 4)
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(days) { day ->
            val isSelected = day == selectedDay
            Card(
                modifier = Modifier
                    .width(60.dp) // Fixed width for the day card
                    .height(40.dp) // Fixed height for the day card
                    .clickable { onDaySelected(day) }, // Handle click to select day
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) PrimaryYellow else Color.Gray.copy(alpha = 0.2f) // Highlight selected day
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Day $day",
                        color = if (isSelected) Color.Black else Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}