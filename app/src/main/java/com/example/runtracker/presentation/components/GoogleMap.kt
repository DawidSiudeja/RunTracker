package com.example.runtracker.presentation.components

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.runtracker.R
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.runtracker.navigation.Screen
import com.example.runtracker.presentation.screens.active_workout.ActiveWorkoutViewModel
import com.example.runtracker.ui.theme.OrangeSecondaryColor
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun GoogleMapContainer(
    listOfPoints: List<LatLng>,
    navController: NavController,
    locationData: Pair<String, String>
) {
    val context = LocalContext.current

    val location: Pair<String, String>

    if (locationData == null) {
        location = Pair("00.00", "00.00")
    } else {
        location = locationData
    }

    var currentUserLocation = LatLng(location.first.toDouble(), location.second.toDouble())

    var currentUserState = MarkerState(position = currentUserLocation)

    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentUserLocation, 15f)
    }

    LaunchedEffect(location) {
        val newCameraPosition = CameraPosition.fromLatLngZoom(currentUserLocation, 15f)
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(newCameraPosition)
        cameraPositionState.animate(cameraUpdate, 1000)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            MapMarker(
                context = context,
                position = currentUserState,
                title = "",
                snippet = "",
                iconResourceId = R.drawable.ic_marker
            )
            Polyline(
                points = listOfPoints,
                color = OrangeSecondaryColor
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            contentDescription = "Close activity",
            modifier = Modifier
                .size(64.dp)
                .clickable {
                    navController.navigate(Screen.Home.route)
                }
                .padding(16.dp)
                .align(Alignment.TopStart)
        )
    }
}


@Composable
fun MapMarker(
    context: Context,
    position: MarkerState,
    title: String,
    snippet: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptor(
        context, iconResourceId
    )
    Marker(
        state = position,
        title = title,
        snippet = snippet,
        icon = icon,
    )
}

fun bitmapDescriptor(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
