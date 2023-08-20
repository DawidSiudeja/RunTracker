package com.example.runtracker.presentation.screens.ended_workout

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.presentation.screens.active_workout.Stats
import com.example.runtracker.presentation.screens.home.GoogleMapContainer

@Composable
fun EndedWorkout(
    workoutId: String,
    navController: NavController,
    viewModel: EndedWorkoutViewModel = hiltViewModel(),
) {

    val workout = viewModel.getWorkout(workoutId = workoutId).collectAsState(Workout()).value
    val points = viewModel.convertStringToListOfLatLng(workout.points)

    var avgSpeedKmPerH by remember { mutableStateOf(0.0) }

    if (workout.distance > 0 && workout.time > 0) {
        avgSpeedKmPerH = (workout.distance * 1000) / (workout.time * 60 * 60)
    }

    Text(text = "Workout ID: ${workout.id}")

    Column {
        GoogleMapContainer(
            listOfPoints = points,
            navController = navController
        )

        Stats(
            distance = workout.distance,
            kcal = workout.kcal,
            time = workout.time,
            avgSpeedKmPerH = avgSpeedKmPerH
        )
    }

}