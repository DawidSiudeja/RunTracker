package com.example.runtracker.presentation.screens.latest_workouts

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.navigation.Screen
import com.example.runtracker.presentation.components.SectionTitle
import com.example.runtracker.presentation.components.Title
import com.example.runtracker.presentation.menu.Menu
import com.example.runtracker.presentation.components.WorkoutItem
import com.example.runtracker.ui.theme.backgroundColor

@Composable
fun LatestWorkoutsScreen(
    navController: NavController,
    viewModel: LatestWorkoutsViewModel = hiltViewModel(),
) {

    val workouts = viewModel.getAllWorkouts().collectAsState(emptyList()).value.reversed()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(backgroundColor),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.85f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(20.dp),
            ) {
                Title(title = "your Workouts")
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
            ) {
                SectionTitle(title = "Last workouts")
            }
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
        contentPadding = PaddingValues(start = 20.dp, top = 10.dp, end = 20.dp),
        modifier = Modifier.fillMaxHeight(),
    ) {
        items(workouts.size) {
            WorkoutItem(workout = workouts[it], navController)
        }
    }
}

