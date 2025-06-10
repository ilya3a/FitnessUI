package com.yoyo.fitnessui.data.repository

import android.content.Context
import com.google.gson.Gson
import com.yoyo.fitnessui.data.model.WorkoutData
import java.io.InputStreamReader

object WorkoutRepository {
    fun loadWorkouts(context: Context): WorkoutData {
        val inputStream = context.assets.open("workouts.json")
        val reader = InputStreamReader(inputStream)
        return Gson().fromJson(reader, WorkoutData::class.java)
    }
}