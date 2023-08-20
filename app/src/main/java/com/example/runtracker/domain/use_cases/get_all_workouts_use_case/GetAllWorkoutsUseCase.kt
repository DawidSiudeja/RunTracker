package com.example.runtracker.domain.use_cases.get_all_workouts_use_case

import androidx.compose.runtime.collectAsState
import com.example.runtracker.data.local.repository.WorkoutRepository
import com.example.runtracker.domain.models.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GetAllWorkoutsUseCase(
    private val repository: WorkoutRepository
) {
    fun execute(): Flow<List<Workout>> {
        return repository.getAllWorkouts()
    }
}