package com.example.runtracker.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info_table")
data class UserInfo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val nickname: String = "Your nick",

    @ColumnInfo
    val userWeight: Int = 70,

    @ColumnInfo
    val age: Int = 25,

    // Monthly goal
    @ColumnInfo
    val kilometersGoal: Int = 50,

    // Monthly goal
    @ColumnInfo
    val workoutsGoal: Int = 10,

    // Daily goal
    @ColumnInfo
    val stepsGoal: Int = 10000,
)