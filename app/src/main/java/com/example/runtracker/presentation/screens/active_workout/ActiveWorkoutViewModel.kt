package com.example.runtracker.presentation.screens.active_workout

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.data.local.Workout
import com.example.runtracker.gps.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.*

@HiltViewModel
class ActiveWorkoutViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {

    private var workoutId = 0

    private var locationCallback: ((Pair<String, String>) -> Unit)? = null

    fun subscribeToObservers(viewLifecycleOwner: LifecycleOwner, callback: (Pair<String, String>) -> Unit) {
        locationCallback = callback
        LocationService.locationLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("LocationT", "ActiveWorkoutViewModel. Lat: ${it.first}, Long: ${it.second}")
            locationCallback?.invoke(it)

            viewModelScope.launch {

                val currentWorkout = appDatabase.workoutDao().getSpecifWorkout(workoutId).first()

                if (workoutId != 0 && currentWorkout.isActive) {

                    var newPoints: MutableList<String> = mutableListOf()

                    val pointsString = it.first + ", " + it.second
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

            val listOfWorkouts = appDatabase.workoutDao().getAllWorkouts().first().size
            val workout = Workout()
            workoutId = listOfWorkouts + 1
            appDatabase.workoutDao().addWorkout(workout)


            Log.d("WORKOUT", "Workout ID: $workoutId")

        }
    }

    fun endWorkout() {
        viewModelScope.launch {
            appDatabase.workoutDao().setInactiveWorkout(isActive = false)
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