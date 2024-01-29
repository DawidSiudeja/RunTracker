package com.example.runtracker.presentation.screens.active_workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.R
import com.example.runtracker.navigation.Screen
import com.example.runtracker.presentation.components.navigate
import com.example.runtracker.ui.theme.OrangeSecondaryColor

@Composable
fun ActiveScreen(
    navController: NavController,
    viewModel: ActiveWorkoutViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    viewModel.startTimer()

    var location by remember { mutableStateOf(Pair("50.75", "19.10")) }

    var isTimerRunning by remember { mutableStateOf(false) }

    val timerValue by viewModel.timer.collectAsState()

    val distance: Double by viewModel.distance.collectAsState()

    val minutesPerKM: Double by viewModel.minutesPerKm.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        TimerScreenContent(timerValue)
        Spacer()
        DistanceInKilometers(distance)
        Spacer()
        MinutesPerKm(minutesPerKM)
        ButtonEndWorkout(navController, viewModel)

    }
}

@Composable
fun DistanceInKilometers(distance: Double) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = String.format("%.2f", distance),
            fontSize = 70.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Distance [m]",
            fontSize = 16.sp
        )
    }
}

@Composable
fun MinutesPerKm(minutesPerKm: Double) {

    val minutes = minutesPerKm.toInt()
    val seconds = ((minutesPerKm - minutes) * 60).toInt()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = String.format("%d'%02d''", minutes, seconds),
            fontSize = 70.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Minutes per KM",
            fontSize = 16.sp
        )
    }
}



@Composable
fun TimerScreenContent(timerValue: Int) {
    TimerScreen(
        timerValue = timerValue,
    )
}
@Composable
fun TimerScreen(
    timerValue: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = timerValue.formatTime(),
            fontSize = 70.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Time",
            fontSize = 16.sp
        )
    }
}

@Composable
fun ButtonEndWorkout(navController: NavController, viewModel: ActiveWorkoutViewModel) {
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
            onClick = {
                viewModel.endWorkout()
                navigate(
                    navController = navController,
                    destination = Screen.LatestWorkouts
                )
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pause),
                contentDescription = "End icon",
                Modifier
                    .size(100.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
fun Spacer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.LightGray)
            .padding(vertical = 20.dp)
    )
}

fun Int.formatTime(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val remainingSeconds = this % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}