package com.example.runtracker.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.data.local.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {

    fun setUserWeight(userWeight: Int) {
        viewModelScope.launch {
            appDatabase.userInfoDao().setUserWeight(userWeight = userWeight)
        }
    }

}