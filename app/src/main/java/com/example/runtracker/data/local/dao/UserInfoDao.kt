package com.example.runtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.runtracker.domain.models.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM user_info_table")
    fun getAllUserInfo(): Flow<List<UserInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserInfo(userInfo: UserInfo)

    @Query("UPDATE user_info_table set userWeight = :userWeight")
    suspend fun setUserWeight(userWeight: Int)

}