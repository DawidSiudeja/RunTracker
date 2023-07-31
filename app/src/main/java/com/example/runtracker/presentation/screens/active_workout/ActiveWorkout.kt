package com.example.runtracker.presentation.screens.active_workout

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.data.local.Workout
import com.example.runtracker.presentation.screens.home.GoogleMapContainer
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.first

@Composable
fun ActiveWorkout(
    navController: NavController,
    viewModel: ActiveWorkoutViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var listOfPoints = emptyList<LatLng>()
        var workoutId = viewModel.getAllWorkouts().collectAsState(null).value?.size


        if(workoutId != null) {
            var workout = viewModel.getSpecifWorkout(workoutId).collectAsState(null).value
            if (workout != null) {
                listOfPoints = viewModel.convertStringToListOfLatLng(workout.points)
            }
        }

        GoogleMapContainer(
            listOfPoints = listOfPoints
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(bottom = 30.dp),
            onClick = {
                viewModel.endWorkout()
            }
        ) {
            Text(
                text = "End Workout"
            )
        }


        Button(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(bottom = 30.dp),
            onClick = {
                viewModel.startWorkout()
            }
        ) {
            Text(
                text = "Start Workout"
            )
        }
    }

}