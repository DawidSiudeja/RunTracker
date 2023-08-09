package com.example.runtracker.presentation.screens.latest_workouts

import androidx.lifecycle.ViewModel
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.data.local.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LatestWorkoutsViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {

    fun getAllWorkouts(): Flow<List<Workout>> {
        return appDatabase.workoutDao().getAllWorkouts()
    }

}