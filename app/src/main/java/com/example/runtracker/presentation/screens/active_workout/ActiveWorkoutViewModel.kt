package com.example.runtracker.presentation.screens.active_workout

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.data.local.Workout
import com.example.runtracker.gps.LocationService
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.*

@HiltViewModel
class ActiveWorkoutViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {


    private var workoutId = 0
    private var locationCallback: ((Pair<String, String>) -> Unit)? = null

    init {
        viewModelScope.launch {
            appDatabase.workoutDao().addWorkout(Workout())
        }
    }

    fun getSpecifWorkout(workoutId: Int): Flow<Workout> {
        return appDatabase.workoutDao().getSpecifWorkout(id = workoutId)
    }

    fun getAllWorkouts(): Flow<List<Workout>> {
        return appDatabase.workoutDao().getAllWorkouts()
    }

    fun convertStringToListOfLatLng(input: List<String>): List<LatLng> {
        val latLngList = mutableListOf<LatLng>()

            for (locationString in input) {
                val (latitudeStr, longitudeStr) = locationString.split(", ")
                val latitude = latitudeStr.toDouble()
                val longitude = longitudeStr.toDouble()
                val latLng = LatLng(latitude, longitude)
                latLngList.add(latLng)
            }

        return latLngList
    }


    fun subscribeToObservers(viewLifecycleOwner: LifecycleOwner, callback: (Pair<String, String>) -> Unit) {
        locationCallback = callback
        LocationService.locationLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("LocationT", "ActiveWorkoutViewModel. Lat: ${it.first}, Long: ${it.second}")
            locationCallback?.invoke(it)

            viewModelScope.launch {

                val currentWorkout = appDatabase.workoutDao().getSpecifWorkout(workoutId).first()

                if (currentWorkout.isActive) {

                    var newPoints: MutableList<String> = mutableListOf()

                    val pointsString = it.first + " | " + it.second
                    val oldPoints = appDatabase.workoutDao()
                        .getSpecifWorkout(workoutId).first().points.toMutableList()

                    newPoints.addAll(oldPoints)
                    newPoints.add(pointsString)

                    appDatabase.workoutDao().updateListPointsOfWorkout(
                        points = newPoints.toList(),
                        id = workoutId
                    )

                    calculateDistance(newPoints, workoutId)

                }

            }

        })
    }

    fun startWorkout() {
        viewModelScope.launch {
            appDatabase.workoutDao().setActiveOfWorkout(isActive = true)
        }
    }

    fun endWorkout() {
        viewModelScope.launch {
            appDatabase.workoutDao().setActiveOfWorkout(isActive = false)
        }
    }

    private fun calculateDistance(pointList: MutableList<String>, workoutId: Int) {

        val earthRadius = 6371.0
        var i = pointList.size - 1
        Log.d("WORKOUT", pointList.size.toString())

        if (pointList.size >= 2) {

            val firstLocation = pointList[i].split(",")

            val firstLongitude = firstLocation[0].trim().toDouble()
            val firstLatitude = firstLocation[1].trim().toDouble()


            val secondLocation = pointList[i - 1].split(",")

            val secondLongitude = secondLocation[0].trim().toDouble()
            val secondLatitude = secondLocation[1].trim().toDouble()

            val dLat = Math.toRadians(secondLatitude - firstLatitude)
            val dLon = Math.toRadians(secondLongitude - firstLongitude)

            val lat1 = Math.toRadians(firstLatitude)
            val lat2 = Math.toRadians(secondLongitude)

            val a = sin(dLat / 2) * sin(dLat / 2) + sin(dLon / 2) * sin(dLon / 2) * cos(lat1) * cos(lat2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            val segmentDistance = earthRadius * c * 1000 // Distance in meter

            Log.d("WORKOUT", "DISTANCE: $segmentDistance")

            viewModelScope.launch {
                val currentDistance = appDatabase.workoutDao()
                    .getSpecifWorkout(workoutId).first().distance
                var distance = currentDistance + segmentDistance

                appDatabase.workoutDao().updateDistance(distance, workoutId)
            }

        }

    }
}