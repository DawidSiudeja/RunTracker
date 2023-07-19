package com.example.runtracker.presentation.screens.home

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.gps.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {

    private var locationCallback: ((Pair<String, String>) -> Unit)? = null


    fun startTrackingLocalization(context: Context) {
        Intent(context, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            context.startService(this)
        }
    }

    fun subscribeToObservers(viewLifecycleOwner: LifecycleOwner, callback: (Pair<String, String>) -> Unit) {
        locationCallback = callback
        LocationService.locationLiveData.observe(viewLifecycleOwner, Observer {
            Log.d("LocationT", "HomeViewModel. Lat: ${it.first}, Long: ${it.second}")
            locationCallback?.invoke(it)
        })
    }


}