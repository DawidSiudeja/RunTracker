package com.example.runtracker.presentation.screens.home

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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

    val context = LocalContext.current

    viewModel.startTrackingLocalization(context = context)

    var location by remember { mutableStateOf(Pair("00.00", "00.00")) }

    viewModel.subscribeToObservers(viewLifecycleOwner = LocalLifecycleOwner.current) { it ->
        location = it
    }

    var currentUserLocation = LatLng(location.first.toDouble(), location.second.toDouble())

    var currentUserState = MarkerState(position = currentUserLocation)
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentUserLocation, 15f)
    }

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

            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = currentUserState,
                    title = "test"
                )
            }


        }
        Menu(
            navController = navController,
            currentScreen = "Home"
        )

    }

}


@Composable
fun ButtonsToTestLocation(context: Context) {
    Button(onClick = {
        Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            context.startService(this)
        }
    }) {
        Text("Start")
    }
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = {
        Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            context.startService(this)
        }
    }) {
        Text("Stop")
    }
}