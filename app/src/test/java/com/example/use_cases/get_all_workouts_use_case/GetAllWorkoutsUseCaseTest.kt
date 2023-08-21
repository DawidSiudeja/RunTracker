package com.example.use_cases.get_all_workouts_use_case

import com.example.runtracker.data.local.repository.WorkoutRepository
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.domain.use_cases.get_all_workouts_use_case.GetAllWorkoutsUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetAllWorkoutsUseCaseTest {

    @Test
    fun `Generating list of Workouts`() {
        val workoutRepository = mock(WorkoutRepository::class.java)

        val expectedWorkouts = listOf(
            Workout(id = 0),
            Workout(id = 1),
            Workout(id = 2),
        )

        val expectedFlow = flowOf(expectedWorkouts)

        `when`(workoutRepository.getAllWorkouts()).thenReturn(expectedFlow)

        val getAllWorkoutsUseCase = GetAllWorkoutsUseCase(workoutRepository)

        val result = mutableListOf<Workout>()

        runBlocking {
            getAllWorkoutsUseCase.execute().take(1).collect { workouts ->
                result.addAll(workouts)
            }
        }
        assertEquals(expectedWorkouts[0], result[0])
    }
}