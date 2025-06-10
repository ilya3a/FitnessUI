package com.yoyo.fitnessui.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoyo.fitnessui.data.model.WorkoutData
import com.yoyo.fitnessui.data.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val _workoutData = MutableStateFlow<WorkoutData?>(null)
    val workoutData: StateFlow<WorkoutData?> = _workoutData.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = WorkoutRepository.loadWorkouts(application)
            _workoutData.value = data
        }
    }
}
