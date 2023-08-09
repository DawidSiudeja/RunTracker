package com.example.runtracker.presentation.screens.home

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.data.local.UserInfo
import com.example.runtracker.data.local.Workout
import com.example.runtracker.gps.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {

    init {
        viewModelScope.launch {
            val userInfoSize = appDatabase.userInfoDao().getAllUserInfo().first().size
            if (userInfoSize == 0) {
                appDatabase.userInfoDao().addUserInfo(UserInfo())
            }
        }
    }

}