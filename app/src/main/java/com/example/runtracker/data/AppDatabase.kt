package com.example.runtracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.runtracker.data.local.converter.PointsConverter
import com.example.runtracker.domain.models.UserInfo
import com.example.runtracker.data.local.dao.UserInfoDao
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.data.local.dao.WorkoutDao

@Database(entities = [UserInfo::class, Workout::class], version = 1)
@TypeConverters(PointsConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao
    abstract fun workoutDao(): WorkoutDao

}