package com.example.runtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.runtracker.domain.models.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkout(workout: Workout)

    @Query("SELECT * FROM workouts_table")
    fun getAllWorkouts(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts_table WHERE id = :id")
    fun getSpecifWorkout(id: Int): Flow<Workout>

    @Query("UPDATE workouts_table SET points = :points WHERE id = :id")
    suspend fun updateListPointsOfWorkout(points: List<String>, id: Int)

    @Query("UPDATE workouts_table set kcal = :kcal WHERE id = :id")
    suspend fun updateKcal(kcal: Int, id: Int)

    @Query("UPDATE workouts_table set isActive = :isActive WHERE id = :id")
    suspend fun setActiveOfWorkout(isActive: Boolean, id: Int)

    @Query("UPDATE workouts_table set distance = :distance WHERE id = :id")
    suspend fun updateDistance(distance: Double, id: Int)
    @Query("UPDATE workouts_table set time = :time where id = :id")
    suspend fun updateTimeOfWorkout(time: Int, id: Int)

    @Query("UPDATE workouts_table set minutesPerKm = :minutesPerKm where id = :id")
    suspend fun updateMinutesPerKmOfWorkout(minutesPerKm: Double, id: Int)

    @Query("UPDATE workouts_table set avgSpeed = :avgSpeed where id = :id")
    suspend fun updateAvgSpeedOfWorkout(avgSpeed: Int, id: Int)
}