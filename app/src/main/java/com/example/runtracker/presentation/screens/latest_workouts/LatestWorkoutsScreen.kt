package com.example.runtracker.presentation.screens.latest_workouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.data.local.Workout
import com.example.runtracker.navigation.Screen
import com.example.runtracker.presentation.menu.Menu

@Composable
fun LatestWorkoutsScreen(
    navController: NavController,
    viewModel: LatestWorkoutsViewModel = hiltViewModel(),
) {

    val workouts = viewModel.getAllWorkouts().collectAsState(emptyList()).value.reversed()

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ListOfWorkouts(
                workouts = workouts,
                navController = navController
            )
        }
        Menu(
            navController = navController,
            currentScreen = "Workouts"
        )
    }

}



@Composable
fun ListOfWorkouts(
    workouts: List<Workout>,
    navController: NavController
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(start = 20.dp, top = 10.dp, end = 20.dp, bottom = 30.dp),
        modifier = Modifier.fillMaxHeight(.9f),
    ) {
        items(workouts.size) {
            Column(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clickable {
                        navigate(
                            navController = navController,
                            destination = Screen.EndedWorkout,
                            arguments = listOf(workouts[it].id)
                        )
                    }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = workouts[it].date.substring(0,10),
                        fontSize = 15.sp
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Distance",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text= "${workouts[it].distance.toInt()} m" ,
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Time",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = if (workouts[it].time > 60) {
                                "${(workouts[it].time / 60)} min. ${(workouts[it].time % 60)} s"
                            } else {
                                "${workouts[it].time} s"
                            },
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.LightGray)
                )
            }
        }
    }
}


private fun navigate(navController: NavController, destination: Screen, arguments: List<Any>? = null) {

    var destinationString = destination.route

    if (arguments != null) {
        for (element in arguments) {
            destinationString += "/$element"
        }
    }

    navController.navigate(destinationString)
}