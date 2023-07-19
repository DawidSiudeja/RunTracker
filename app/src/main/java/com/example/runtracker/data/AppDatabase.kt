package com.example.runtracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.runtracker.data.local.UserInfo
import com.example.runtracker.data.local.UserInfoDao

@Database(entities = [UserInfo::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao

}