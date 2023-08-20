package com.example.runtracker.di

import android.content.Context
import androidx.room.Room
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.data.local.dao.WorkoutDao
import com.example.runtracker.data.local.repository.WorkoutRepository
import com.example.runtracker.domain.use_cases.UseCases
import com.example.runtracker.domain.use_cases.get_all_workouts_use_case.GetAllWorkoutsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(appDatabase: AppDatabase): WorkoutDao {
        return appDatabase.workoutDao()
    }

    @Provides
    @Singleton
    fun provideRepository(workoutDao: WorkoutDao): WorkoutRepository {
        return WorkoutRepository(workoutDao)
    }
    @Provides
    @Singleton
    fun provideUseCases(workoutRepository: WorkoutRepository): UseCases {
        return UseCases(
            getAllWorkoutsUseCase = GetAllWorkoutsUseCase(workoutRepository)
        )
    }

}