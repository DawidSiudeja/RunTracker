package com.example.runtracker.gps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocationRepository {

    private val _locationData = MutableLiveData<Pair<Double, Double>>()
    val locationData: LiveData<Pair<Double, Double>> = _locationData

    fun updateLocation(latitude: Double, longitude: Double) {
        _locationData.postValue(Pair(latitude, longitude))
    }

}