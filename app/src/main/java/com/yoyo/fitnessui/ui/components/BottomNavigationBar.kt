package com.yoyo.fitnessui.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yoyo.fitnessui.R
import com.yoyo.fitnessui.ui.theme.DarkBackground
import com.yoyo.fitnessui.ui.theme.FitnessUITheme
import com.yoyo.fitnessui.ui.theme.LightGrayishBlue
import com.yoyo.fitnessui.ui.theme.PrimaryYellow

/**
 * Composable function for the bottom navigation bar.
 * Contains placeholder navigation items for "My Workout", "Progress", and "Settings".
 * In a full app, this would handle actual navigation.
 */
@Composable
fun BottomNavigationBar(
    onMyWorkoutClick: () -> Unit,
    onExerciseClick: () -> Unit,
    onProgressClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    NavigationBar(
        containerColor = DarkBackground, // Dark background for the navigation bar
        modifier = Modifier.height(56.dp)
    ) {
        // Placeholder Navigation Items
        NavigationBarItem(
            selected = true, // Example: "My Workout" is selected
            onClick = { onMyWorkoutClick() },
            icon = { Icon(painterResource(id = R.drawable.workout), contentDescription = "My Workout", tint = PrimaryYellow) }, // Replace with actual icons
            label = { Text("My Workout", color = PrimaryYellow, fontSize = 10.sp) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = DarkBackground // No special indicator color, just blends with background
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { onExerciseClick() },
            icon = { Icon(painterResource(id = R.drawable.ic_sports_handball), contentDescription = "Exercise", tint = LightGrayishBlue) }, // Replace with actual icons
            label = { Text("Progress", color = LightGrayishBlue, fontSize = 10.sp) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onProgressClick() },
            icon = { Icon(painterResource(id = R.drawable.ic_sports_gymnastic), contentDescription = "Progress", tint = LightGrayishBlue) }, // Replace with actual icons
            label = { Text("Settings", color = LightGrayishBlue, fontSize = 10.sp) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onSettingsClick() },
            icon = { Icon(painterResource(id = R.drawable.ic_settings), contentDescription = "Settings", tint = LightGrayishBlue) }, // Replace with actual icons
            label = { Text("Settings", color = LightGrayishBlue, fontSize = 10.sp) }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    FitnessUITheme {
        BottomNavigationBar(
            onMyWorkoutClick = {},
            onProgressClick = {},
            onExerciseClick = {},
            onSettingsClick = {}
        )
    }
}