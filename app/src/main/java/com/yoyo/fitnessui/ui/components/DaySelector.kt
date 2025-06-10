// DaySelector.kt
package com.yoyo.fitnessui.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
// import androidx.compose.foundation.lazy.items // We will use the other overload for items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoyo.fitnessui.ui.theme.FitnessUITheme
import com.yoyo.fitnessui.ui.theme.PrimaryYellow
import androidx.compose.foundation.lazy.itemsIndexed // New import for using index with items
import androidx.compose.foundation.shape.RoundedCornerShape

/**
 * Composable function for selecting a workout day.
 * Displays a horizontal row of clickable day cards.
 *
 * @param selectedDay The currently selected day number.
 * @param onDaySelected Callback function invoked when a day card is clicked, providing the selected day number.
 * @param daysCompletionStatus A map indicating the completion status of each day (Day Number -> Is Completed).
 * @param totalDays The total number of workout days available from the JSON data.
 */
@Composable
fun DaySelector(
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    daysCompletionStatus: Map<Int, Boolean>,
    totalDays: Int
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        // Corrected usage: Use the items overload that takes a count.
        // The 'index' in the lambda will be 0-based, so we add 1 to get the actual day number.
        items(count = totalDays) { index -> // Here's the fix: use 'count' parameter
            val day = index + 1 // Convert 0-based index to 1-based day number
            val isSelected = day == selectedDay
            val isDayCompleted = daysCompletionStatus[day] ?: false

            Card(
                modifier = Modifier
                    .width(80.dp) // Fixed width for each day card
                    .height(48.dp) // Fixed height for each day card
                    .clickable { onDaySelected(day) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) PrimaryYellow else Color.Gray.copy(alpha = 0.2f)
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
                    // Display Check icon if day is completed
                    if (isDayCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Day Completed",
                            tint = if (isSelected) Color.Black else PrimaryYellow,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(top = 4.dp, end = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DaySelectorPreview() {
    FitnessUITheme {
        Column {
            val mockCompletionStatus = mapOf(
                1 to false,
                2 to true,
                3 to false,
                4 to true
            )
            DaySelector(selectedDay = 1, onDaySelected = {}, daysCompletionStatus = mockCompletionStatus, totalDays = 5)
            Spacer(modifier = Modifier.height(16.dp))
            DaySelector(selectedDay = 2, onDaySelected = {}, daysCompletionStatus = mapOf(1 to true, 2 to true), totalDays = 3)
        }
    }
}