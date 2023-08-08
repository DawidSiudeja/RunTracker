package com.example.runtracker.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info_table")
data class UserInfo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val userWeight: Int = 70,

)