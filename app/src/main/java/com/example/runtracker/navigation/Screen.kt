package com.example.runtracker.navigation

sealed class Screen(val route: String) {

    object Home: Screen("home_screen")
    object ActiveWorkout: Screen("training_screen")
    object LatestWorkouts: Screen("latest_workouts_screen")
    object Profile: Screen("profile_screen")
    object EndedWorkout: Screen("ended_workout_screen")
    object Settings: Screen("settings_workout")

}
