package com.example.runtracker.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PointsConverter {

    @TypeConverter
    fun fromListString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toListString(list: List<String>): String {
        return Gson().toJson(list)
    }
}