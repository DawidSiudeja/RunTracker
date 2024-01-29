package com.example.runtracker.presentation.screens.start_workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.runtracker.R
import com.example.runtracker.navigation.Screen
import com.example.runtracker.presentation.components.navigate
import com.example.runtracker.presentation.menu.Menu
import com.example.runtracker.presentation.components.GoogleMapContainer
import com.example.runtracker.ui.theme.OrangeSecondaryColor
import com.google.android.gms.maps.model.LatLng

@Composable
fun StartWorkout(
    navController: NavController,
    viewModel: StartWorkoutViewModel = hiltViewModel(),

) {
    var location by remember {
        mutableStateOf(viewModel.locationData.value.let { pair ->
            Pair(
                pair?.first.toString(),
                pair?.second.toString()
            )
        })
    }

    LaunchedEffect(Unit) {
        val locationDataObserver = Observer<Pair<Double, Double>> { newLocationData ->
            location = Pair(newLocationData.first.toString(), newLocationData.second.toString())
        }
        viewModel.locationData.observeForever(locationDataObserver)
    }



    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val listOfPoints by remember { mutableStateOf(emptyList<LatLng>()) }

        Column {
            GoogleMapContainer(
                listOfPoints = listOfPoints,
                navController = navController,
                locationData = location
            )
        }

        ButtonStartWorkout(navController)
        Menu(
            navController = navController,
            currentScreen = "Start"
        )
    }

}


@Composable
fun ButtonStartWorkout(navController: NavController) {
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
                navigate(
                    navController = navController,
                    destination = Screen.ActiveWorkout
                )
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_start),
                contentDescription = "Start icon",
                Modifier
                    .size(100.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}