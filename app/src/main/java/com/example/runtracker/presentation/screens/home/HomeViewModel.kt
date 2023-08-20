package com.example.runtracker.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.domain.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
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