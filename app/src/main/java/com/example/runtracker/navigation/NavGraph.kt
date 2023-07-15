package com.example.runtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

        }

        composable(
            route = Screen.Workout.route
        ) {

        }

        composable(
            route = Screen.LatestWorkouts.route
        ) {

        }

        composable(
            route = Screen.Profile.route
        ) {

        }

        composable(
            route = Screen.EndedWorkout.route
        ) {

        }

        composable(
            route = Screen.Settings.route
        ) {

        }

    }
}