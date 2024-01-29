package com.example.runtracker.presentation.screens.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.ColumnInfo
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.domain.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {

    fun getUserInfo(): Flow<List<UserInfo>> {
        return appDatabase.userInfoDao().getAllUserInfo()
    }

    fun saveUserData(context: Context, textName: String, textAge: String, textWeight: String, textWorkouts: String, textKilometers: String) {
        viewModelScope.launch {
            appDatabase.userInfoDao().saveUserInfo(
                textName,
                textAge.toInt(),
                textWeight.toInt(),
                textWorkouts.toInt(),
                textKilometers.toInt()
            )
        }
        Toast.makeText(context, "Successfully saved data", Toast.LENGTH_SHORT).show()

    }

}