package com.yoyo.fitnessui.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the root structure of the workouts JSON.
 * It contains a list of [WorkoutDay] objects.
 */
data class WorkoutData(
    val workouts: List<WorkoutDay>
)

/**
 * Data class representing a single day's workout.
 * Contains the [day] number and a list of [Exercise] objects for that day.
 */
data class WorkoutDay(
    val day: Int,
    val workout: List<Exercise>
)

/**
 * Data class representing a single exercise within a workout day.
 * Contains various details about the exercise, including its ID, name,
 * thumbnail, muscle group, sets, reps, and optional weight.
 */
data class Exercise(
    @SerializedName("exercise_id") val exerciseId: Int,
    @SerializedName("exercise_name") val exerciseName: String,
    @SerializedName("exercise_thumbnail") val exerciseThumbnail: String,
    @SerializedName("muscle_group") val muscleGroup: String,
    @SerializedName("muscle_group_image") val muscleGroupImage: String,
    @SerializedName("amount_of_sets") val amountOfSets: Int,
    @SerializedName("rep_range") val repRange: String,
    @SerializedName("weight_amount") val weightAmount: String? // Can be null in JSON
)