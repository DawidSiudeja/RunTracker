package com.example.runtracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.runtracker.data.local.PointsConverter
import com.example.runtracker.data.local.UserInfo
import com.example.runtracker.data.local.UserInfoDao
import com.example.runtracker.data.local.Workout
import com.example.runtracker.data.local.WorkoutDao

@Database(entities = [UserInfo::class, Workout::class], version = 1)
@TypeConverters(PointsConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao
    abstract fun workoutDao(): WorkoutDao

}