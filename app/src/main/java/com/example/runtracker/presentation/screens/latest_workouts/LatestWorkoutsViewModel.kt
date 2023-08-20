package com.example.runtracker.presentation.screens.latest_workouts

import androidx.lifecycle.ViewModel
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LatestWorkoutsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    fun getAllWorkouts(): Flow<List<Workout>> {
        return useCases.getAllWorkoutsUseCase.execute()
    }

}