package com.example.runtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.runtracker.presentation.screens.home.HomeScreen
import com.example.runtracker.presentation.screens.profile.ProfileScreen
import com.example.runtracker.presentation.screens.settings.SettingsScreen
import com.example.runtracker.presentation.screens.workouts.LatestWorkoutsScreen

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.Workout.route
        ) {
            
        }

        composable(
            route = Screen.LatestWorkouts.route
        ) {
            LatestWorkoutsScreen(navController = navController)
        }

        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreen(navController = navController)
        }

        composable(
            route = Screen.EndedWorkout.route
        ) {

        }

        composable(
            route = Screen.Settings.route
        ) {
            SettingsScreen(navController = navController)
        }

    }
}