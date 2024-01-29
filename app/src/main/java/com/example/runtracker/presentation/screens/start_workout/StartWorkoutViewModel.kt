package com.example.runtracker.presentation.screens.start_workout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.runtracker.gps.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartWorkoutViewModel @Inject constructor(
    private val locationRepository: LocationRepository
): ViewModel() {

    var locationData: LiveData<Pair<Double, Double>> = locationRepository.locationData

}