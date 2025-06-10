// WorkoutViewModel.kt
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
    // It will now contain exercises with their completion status.
    private val _currentWorkoutDay = MutableStateFlow<WorkoutDay?>(null)
    val currentWorkoutDay: StateFlow<WorkoutDay?> = _currentWorkoutDay.asStateFlow()

    // Exposed StateFlow for the UI, representing the currently selected day number.
    private val _selectedDay = MutableStateFlow(1) // Default to Day 1
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    // Map to store completion status for each day (Day Number -> Is Completed)
    private val _daysCompletionStatus = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val daysCompletionStatus: StateFlow<Map<Int, Boolean>> = _daysCompletionStatus.asStateFlow()

    // Holds the total number of days available in the workout data.
    private val _totalDays = MutableStateFlow(0)
    val totalDays: StateFlow<Int> = _totalDays.asStateFlow()

    init {
        loadWorkoutData()
    }

    /**
     * Loads workout data from the repository and initializes related states.
     */
    private fun loadWorkoutData() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = WorkoutRepository.loadWorkouts(getApplication())
            _allWorkoutData.value = data

            data?.let {
                // Initialize total days by counting the number of workout days in the loaded data.
                _totalDays.value = it.workouts.size // THIS IS THE CRUCIAL LINE for total days

                // Initialize completion status for all days based on the WorkoutDay's 'isCompleted' status
                val initialCompletionMap = it.workouts.associate { workoutDay ->
                    // For initial load, we assume 'isCompleted' in WorkoutDay reflects actual completion
                    workoutDay.day to workoutDay.workout.all { exercise -> exercise.isCompleted }
                }
                _daysCompletionStatus.value = initialCompletionMap

                // Set initial current workout day (default to day 1 or first available)
                updateCurrentWorkoutDay()
            }
        }
    }

    /**
     * Selects a new workout day.
     * @param day The day number to select.
     */
    fun selectDay(day: Int) {
        // Ensure selected day is within the bounds of available days from JSON
        if (day >= 1 && day <= _totalDays.value) {
            _selectedDay.value = day
            updateCurrentWorkoutDay()
        }
    }

    /**
     * Toggles the completion status of a specific exercise within a specific day.
     * @param day The day number of the exercise.
     * @param exerciseId The ID of the exercise to toggle.
     */
    fun toggleExerciseCompletion(day: Int, exerciseId: Int) {
        _allWorkoutData.value = _allWorkoutData.value?.copy(
            workouts = _allWorkoutData.value?.workouts?.map { workoutDay ->
                if (workoutDay.day == day) {
                    val updatedWorkout = workoutDay.workout.map { exercise ->
                        if (exercise.exerciseId == exerciseId) {
                            exercise.copy(isCompleted = !exercise.isCompleted)
                        } else {
                            exercise
                        }
                    }
                    // After updating exercises, check if the entire day is now completed
                    val isDayFullyCompleted = updatedWorkout.all { it.isCompleted }
                    // Update the isCompleted status of the WorkoutDay itself
                    workoutDay.copy(workout = updatedWorkout, isCompleted = isDayFullyCompleted)
                } else {
                    workoutDay
                }
            } ?: emptyList()
        )
        // After updating _allWorkoutData, ensure _daysCompletionStatus and _currentWorkoutDay are also updated
        updateDaysCompletionStatus() // Call to update the map of day completion statuses
        updateCurrentWorkoutDay() // Update current day displayed
    }

    /**
     * Updates the [currentWorkoutDay] based on the currently [_selectedDay].
     * This function is called internally when the selected day changes or data is loaded/updated.
     */
    private fun updateCurrentWorkoutDay() {
        _currentWorkoutDay.value = _allWorkoutData.value?.workouts?.find { it.day == _selectedDay.value }
    }

    /**
     * Function to update the `_daysCompletionStatus` map.
     * This iterates through all workout days and updates their completion status in the map,
     * based on the `isCompleted` property of each `WorkoutDay`.
     */
    private fun updateDaysCompletionStatus() {
        val newStatusMap = _allWorkoutData.value?.workouts?.associate { workoutDay ->
            workoutDay.day to workoutDay.isCompleted
        } ?: emptyMap()
        _daysCompletionStatus.value = newStatusMap
    }
}