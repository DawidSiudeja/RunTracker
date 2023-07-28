package com.example.runtracker.presentation.screens.latest_workouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.runtracker.presentation.menu.Menu

@Composable
fun LatestWorkoutsScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = "Workouts"
        )
        Menu(
            navController = navController,
            currentScreen = "Workouts"
        )
    }

}