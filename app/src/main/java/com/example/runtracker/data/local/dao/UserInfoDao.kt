package com.example.runtracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.runtracker.domain.models.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM user_info_table")
    fun getAllUserInfo(): Flow<List<UserInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserInfo(userInfo: UserInfo)

    @Query("UPDATE user_info_table SET nickname = :textName, age = :age, userWeight = :weight, workoutsGoal = :workouts, kilometersGoal = :kilometers")
    suspend fun saveUserInfo(
        textName: String,
        age: Int,
        weight: Int,
        workouts: Int,
        kilometers: Int
    )


}