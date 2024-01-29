package com.example.runtracker.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

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
    val minutesPerKm: Double = 0.00,

    @ColumnInfo
    val avgSpeed: Int = 0,

    @ColumnInfo
    val isActive: Boolean = false,

    @ColumnInfo
    val date: String = LocalDateTime.now().toString(),

    @ColumnInfo
    val time: Int = 0,

)
