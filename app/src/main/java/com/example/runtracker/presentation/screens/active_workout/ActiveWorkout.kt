package com.example.runtracker.presentation.screens.active_workout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.data.local.Workout
import com.example.runtracker.presentation.screens.home.GoogleMapContainer
import com.example.runtracker.ui.theme.OrangeSecondaryColor
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun ActiveWorkout(
    navController: NavController,
    viewModel: ActiveWorkoutViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        var listOfPoints by remember { mutableStateOf(emptyList<LatLng>()) }
        var isActive by remember { mutableStateOf(false) }
        var distance by remember { mutableStateOf(0.00) }
        var kcal by remember { mutableStateOf(0.00) }
        var time by remember { mutableStateOf(0) }

        var avgSpeedKmPerH by remember { mutableStateOf(0.0) }

        if (distance > 0 && time > 0) {
            avgSpeedKmPerH = (distance * 1000) / (time * 60 * 60)
        }


        var i = 0
        var workoutId: Int? = 0

        LaunchedEffect(Unit) {
            while (true) {
                workoutId = viewModel.getAllWorkouts().firstOrNull()?.size
                if (workoutId != null) {
                    val workout = viewModel.getSpecifWorkout(workoutId!!).firstOrNull()
                    if (workout != null) {
                        isActive = workout.isActive
                        if (workout.isActive) {
                            viewModel.addConstValueOfTime()
                            val points = viewModel.convertStringToListOfLatLng(workout.points)
                            listOfPoints = points
                            distance = workout.distance
                            kcal = workout.kcal
                            time = workout.time
                        }
                    }
                }
                i++

                delay(5000)
            }
        }

        Column {
            GoogleMapContainer(
                listOfPoints = listOfPoints,
                navController = navController
            )

            Stats(
                distance = distance,
                kcal = kcal,
                time = time,
                avgSpeedKmPerH = avgSpeedKmPerH
            )
        }

        ButtonStartEndWorkout(
            startWorkout = { viewModel.startWorkout() },
            endWorkout = { viewModel.endWorkout() },
            isWorkoutActive = isActive
        )
    }

}


@Composable
fun ButtonStartEndWorkout(
    startWorkout: () -> Unit,
    endWorkout: () -> Unit,
    isWorkoutActive: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .width(120.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(60.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = OrangeSecondaryColor
            ),
            onClick = if(isWorkoutActive) endWorkout else startWorkout
        ) {
            Text(
                text = if (isWorkoutActive) "End Workout" else "Start Workout",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}


@Composable
fun Stats(
    distance: Double,
    kcal: Double,
    time: Int,
    avgSpeedKmPerH: Double
) {

    val roundedDistance = String.format("%.2f", distance)
    val roundedAvgSpeedKmPerH = String.format("%.2f", avgSpeedKmPerH)
    val roundedKcal = kcal.toString()

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Distance",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "$roundedDistance meters"
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Kcal",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = roundedKcal
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Avg speed",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "$roundedAvgSpeedKmPerH km/h"
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Time",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "$time s"
                )
            }
        }
    }

}