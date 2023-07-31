package com.example.runtracker.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts_table")
data class Workout(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val points: List<String> = emptyList(),

    @ColumnInfo
    val distance: Double = 0.00,

    @ColumnInfo
    val kcal: Double = 0.00,

    @ColumnInfo
    val isActive: Boolean = false,

)
