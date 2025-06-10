package com.yoyo.fitnessui.data.repository

import android.content.Context
import com.google.gson.Gson
import com.yoyo.fitnessui.data.model.WorkoutData
import java.io.IOException
import java.io.InputStreamReader

/**
 * Singleton repository object responsible for loading workout data from the assets folder.
 * This class uses GSON for JSON parsing.
 */
object WorkoutRepository {

    /**
     * Loads workout data from the 'workouts.json' file located in the application's assets.
     *
     * @param context The application context, used to access assets.
     * @return [WorkoutData] object containing the parsed workout information, or null if an error occurs.
     */
    fun loadWorkouts(context: Context): WorkoutData? { // Changed return type to nullable for error handling
        return try {
            val inputStream = context.assets.open("workouts.json")
            val reader = InputStreamReader(inputStream)
            Gson().fromJson(reader, WorkoutData::class.java)
        } catch (ioException: IOException) {
            // Log the exception for debugging purposes
            ioException.printStackTrace()
            null
        } catch (e: Exception) {
            // Catch any other exceptions during JSON parsing
            e.printStackTrace()
            null
        }
    }
}