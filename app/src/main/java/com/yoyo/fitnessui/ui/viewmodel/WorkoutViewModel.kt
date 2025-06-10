package com.yoyo.fitnessui.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yoyo.fitnessui.data.model.WorkoutData
import com.yoyo.fitnessui.data.model.WorkoutDay
import com.yoyo.fitnessui.data.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the workout screen.
 * Manages the state of workout data, selected day, and current workout details.
 */
class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    // Holds all workout data loaded from the JSON.
    private val _allWorkoutData = MutableStateFlow<WorkoutData?>(null)

    // Exposed StateFlow for the UI, representing the currently displayed workout day.
    private val _currentWorkoutDay = MutableStateFlow<WorkoutDay?>(null)
    val currentWorkoutDay: StateFlow<WorkoutDay?> = _currentWorkoutDay.asStateFlow()

    // Exposed StateFlow for the UI, representing the currently selected day number.
    private val _selectedDay = MutableStateFlow(1) // Default to Day 1
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    init {
        loadWorkoutsData()
    }

    /**
     * Loads workout data from the repository.
     * This operation is performed in a background coroutine to avoid blocking the UI thread.
     */
    private fun loadWorkoutsData() {
        viewModelScope.launch(Dispatchers.IO) { // Ensure this runs on an IO thread
            val data = WorkoutRepository.loadWorkouts(getApplication()) // Use getApplication() for Context
            _allWorkoutData.value = data
            // Set the first day available from the loaded data as the default selected day.
            // If no workouts are loaded, default to Day 1.
            _selectedDay.value = data?.workouts?.firstOrNull()?.day ?: 1
            updateCurrentWorkoutDay()
        }
    }

    /**
     * Updates the selected day and consequently the current workout day displayed.
     * @param day The day number to select.
     */
    fun selectDay(day: Int) {
        _selectedDay.value = day
        updateCurrentWorkoutDay()
    }

    /**
     * Updates the [currentWorkoutDay] based on the currently [_selectedDay].
     * This function is called internally when the selected day changes or data is loaded.
     */
    private fun updateCurrentWorkoutDay() {
        _currentWorkoutDay.value = _allWorkoutData.value?.workouts?.find { it.day == _selectedDay.value }
    }
}