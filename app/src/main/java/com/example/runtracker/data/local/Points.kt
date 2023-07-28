package com.example.runtracker.data.local

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Points(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val latitude: String,

    @ColumnInfo
    val longitude: String,

)
