package com.example.runtracker.presentation.screens.ended_workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.presentation.components.WorkoutItemDetails
import com.example.runtracker.presentation.menu.Menu
import com.example.runtracker.presentation.components.GoogleMapContainer

@Composable
fun EndedWorkout(
    workoutId: String,
    navController: NavController,
    viewModel: EndedWorkoutViewModel = hiltViewModel(),
) {

    val workout = viewModel.getWorkout(workoutId = workoutId).collectAsState(Workout()).value
    val points = viewModel.convertStringToListOfLatLng(workout.points)

    val location = viewModel.locationData.value.let { pair ->
        Pair(
            pair?.first.toString(),
            pair?.second.toString()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.85f)
                .padding()
                .verticalScroll(rememberScrollState())
        ) {

            GoogleMapContainer(
                listOfPoints = points,
                navController = navController,
                locationData = location
            )

            Row(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                WorkoutItemDetails(workout)
            }

        }

        Menu(
            navController = navController,
            currentScreen = "Workouts"
        )
    }

}