package com.example.runtracker.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.domain.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {


    fun getUserWeight(): Flow<List<UserInfo>> {
        return appDatabase.userInfoDao().getAllUserInfo()
    }

    fun setUserWeight(userWeight: Int) {
        viewModelScope.launch {
            appDatabase.userInfoDao().setUserWeight(userWeight = userWeight)
        }
    }

}