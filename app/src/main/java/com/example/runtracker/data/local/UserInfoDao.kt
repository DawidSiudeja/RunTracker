package com.example.runtracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM user_info_table")
    fun getAllUserInfo(): Flow<UserInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserInfo(userInfo: UserInfo)

    @Query("UPDATE user_info_table SET latitude = :latitude")
    suspend fun setLatitudeValue(latitude: String)

    @Query("UPDATE user_info_table SET longitude = :longitude")
    suspend fun setLongitudeValue(longitude: String)

}