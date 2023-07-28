package com.example.runtracker.presentation.screens.active_workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.presentation.screens.home.GoogleMapContainer

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

        GoogleMapContainer()

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