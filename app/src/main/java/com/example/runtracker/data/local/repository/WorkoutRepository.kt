package com.example.runtracker.data.local.repository

import com.example.runtracker.data.local.dao.WorkoutDao
import com.example.runtracker.domain.models.Workout
import kotlinx.coroutines.flow.Flow

class WorkoutRepository(
    private val workoutDao: WorkoutDao
) {

    suspend fun addWorkout(workout: Workout)
        = workoutDao.addWorkout(workout)

    fun getAllWorkouts(): Flow<List<Workout>>
        = workoutDao.getAllWorkouts()

    fun getSpecifWorkout(id: Int): Flow<Workout>
        = workoutDao.getSpecifWorkout(id)

    suspend fun updateListPointsOfWorkout(points: List<String>, id: Int)
        = workoutDao.updateListPointsOfWorkout(points, id)

    suspend fun updateKcal(kcal: Double, id: Int)
        = workoutDao.updateKcal(kcal, id)

    suspend fun setActiveOfWorkout(isActive: Boolean, id: Int)
        = workoutDao.setActiveOfWorkout(isActive, id)

    suspend fun updateDistance(distance: Double, id: Int)
        = workoutDao.updateDistance(distance, id)

    suspend fun addConstValueOfTime(id: Int)
        = workoutDao.addConstValueOfTime(id)

}