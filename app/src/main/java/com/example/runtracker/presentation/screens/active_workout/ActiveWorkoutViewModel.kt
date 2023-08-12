package com.example.runtracker.presentation.screens.active_workout

import android.content.Context
import android.content.Intent
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
import kotlinx.coroutines.flow.Flow
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

    fun startTrackingLocalization(context: Context) {
        Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            context.startService(this)
        }
    }

    fun subscribeToObservers(viewLifecycleOwner: LifecycleOwner, callback: (Pair<String, String>) -> Unit) {
        locationCallback = callback
        LocationService.locationLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("LocationT", "ActiveWorkoutViewModel. Lat: ${it.first}, Long: ${it.second}")
            locationCallback?.invoke(it)

            viewModelScope.launch {

                workoutId = appDatabase.workoutDao().getAllWorkouts().first().size
                val currentWorkout = appDatabase.workoutDao().getSpecifWorkout(workoutId).first()

                if (workoutId != 0) {
                    if (currentWorkout.isActive) {

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

                        var speedKmPerHour = 0.0

                        if (currentWorkout.distance > 0 && currentWorkout.time > 0) {
                            speedKmPerHour = (currentWorkout.distance * 1000) / (currentWorkout.time * 60 * 60)
                        }

                        val userWeight = appDatabase.userInfoDao().getAllUserInfo().first()[0].userWeight

                        var burnedKcal = calculateKcal(
                            speedKmPerHour = speedKmPerHour,
                            time = currentWorkout.time,
                            userWeight = userWeight
                        )

                        appDatabase.workoutDao().updateKcal(burnedKcal, workoutId)

                        calculateDistance(newPoints, workoutId)

                    }
                }
            }

        })
    }

    fun addConstValueOfTime() {
        viewModelScope.launch {
            appDatabase.workoutDao().addConstValueOfTime(id = workoutId)
        }
    }

    fun startWorkout() {
        viewModelScope.launch {
            appDatabase.workoutDao().addWorkout(Workout())
            val id = appDatabase.workoutDao().getAllWorkouts().first().size
            appDatabase.workoutDao().setActiveOfWorkout(isActive = true, id = id)
        }
    }

    fun endWorkout() {
        viewModelScope.launch {
            val id = appDatabase.workoutDao().getAllWorkouts().first().size
            appDatabase.workoutDao().setActiveOfWorkout(isActive = false, id = id)
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

    private fun calculateMet(speedKmPerHour: Double): Double {
        val metValue = when {
            speedKmPerHour == 0.0 -> 0.0
            speedKmPerHour < 3.2 -> 2.0 // przechadzka // Slow walk
            speedKmPerHour < 4.8 -> 3.0 // spacer // Medium walk
            speedKmPerHour < 6.4 -> 4.0 // szybki spacer // High walk
            speedKmPerHour < 8.0 -> 7.0 // wolny bieg // Slow run
            speedKmPerHour < 10.0 -> 9.0 // średni bieg // Medium run
            else -> 12.0 // szybki bieg // High run
        }
        return metValue
    }

    fun calculateKcal(
        speedKmPerHour: Double,
        userWeight: Int,
        time: Int
    ): Double {
        val met = calculateMet(speedKmPerHour)
        return met * userWeight * (time / 3600)
    }


}