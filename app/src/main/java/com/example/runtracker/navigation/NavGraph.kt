package com.example.runtracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.runtracker.presentation.screens.active_workout.ActiveScreen
import com.example.runtracker.presentation.screens.start_workout.StartWorkout
import com.example.runtracker.presentation.screens.ended_workout.EndedWorkout
import com.example.runtracker.presentation.screens.home.HomeScreen
import com.example.runtracker.presentation.screens.profile.ProfileScreen
import com.example.runtracker.presentation.screens.settings.SettingsScreen
import com.example.runtracker.presentation.screens.latest_workouts.LatestWorkoutsScreen

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
            route = Screen.StartWorkout.route
        ) {
            StartWorkout(navController = navController)
        }

        composable(
            route = Screen.ActiveWorkout.route
        ) {
            ActiveScreen(navController = navController)
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
            route = Screen.EndedWorkout.route + "/{workoutId}",
            arguments = listOf(
                navArgument(
                    name = "workoutId"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->

            val workoutId = requireNotNull(backStackEntry.arguments).getString("workoutId").toString()

            EndedWorkout(
                navController = navController,
                workoutId = workoutId
            )
        }

        composable(
            route = Screen.Settings.route
        ) {
            SettingsScreen(navController = navController)
        }

    }
}