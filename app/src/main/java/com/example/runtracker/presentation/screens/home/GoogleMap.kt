package com.example.runtracker.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.runtracker.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.runtracker.presentation.screens.active_workout.ActiveWorkoutViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GoogleMapContainer(
    viewModelHome: HomeViewModel = hiltViewModel(),
    viewModelActiveWorkout: ActiveWorkoutViewModel = hiltViewModel(),
    listOfPoints: List<LatLng>
) {

    val context = LocalContext.current

    viewModelHome.startTrackingLocalization(context = context)

    var location by remember { mutableStateOf(Pair("00.00", "00.00")) }

    viewModelActiveWorkout.subscribeToObservers(viewLifecycleOwner = LocalLifecycleOwner.current) { it ->
        location = it
    }

    var currentUserLocation = LatLng(location.first.toDouble(), location.second.toDouble())

    var currentUserState = MarkerState(position = currentUserLocation)
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentUserLocation, 15f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = currentUserState,
            title = "test",
            //icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)
        )
        Polyline(
            points = listOfPoints
        )
    }

}
