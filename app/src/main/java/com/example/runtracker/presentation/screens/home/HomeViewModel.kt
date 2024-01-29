package com.example.runtracker.presentation.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.domain.models.UserInfo
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.gps.LocationRepository
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

    fun getUserInfoData(): Flow<List<UserInfo>> {
        return appDatabase.userInfoDao().getAllUserInfo()
    }

    fun getAllWorkouts(): Flow<List<Workout>> {
        return appDatabase.workoutDao().getAllWorkouts()
    }

}