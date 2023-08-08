package com.example.runtracker.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.data.local.Workout
import com.example.runtracker.gps.LocationService
import com.example.runtracker.presentation.menu.Menu
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
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
                workouts = workouts
            )
        }
        Menu(
            navController = navController,
            currentScreen = "Home"
        )
    }

}


@Composable
fun ListOfWorkouts(
    workouts: List<Workout>
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
                            text = "Kcal",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text= workouts[it].kcal.toInt().toString(),
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