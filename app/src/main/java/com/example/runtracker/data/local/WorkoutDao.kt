package com.example.runtracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    suspend fun updateKcal(kcal: Double, id: Int)

    @Query("UPDATE workouts_table set isActive = :isActive WHERE id = :id")
    suspend fun setActiveOfWorkout(isActive: Boolean, id: Int)

    @Query("UPDATE workouts_table set distance = :distance WHERE id = :id")
    suspend fun updateDistance(distance: Double, id: Int)

    @Query("UPDATE workouts_table set time = time + 5 WHERE id = :id")
    suspend fun addConstValueOfTime(id: Int)

}