package com.example.runtracker.presentation.screens.ended_workout

import androidx.lifecycle.ViewModel
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.domain.models.Workout
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class EndedWorkoutViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel(){

    fun getWorkout(workoutId: String): Flow<Workout> {
        return appDatabase.workoutDao().getSpecifWorkout(workoutId.toInt())
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

}