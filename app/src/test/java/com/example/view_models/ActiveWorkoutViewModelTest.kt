package com.example.view_models

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.gps.LocationRepository
import com.example.runtracker.presentation.screens.active_workout.ActiveWorkoutViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Context
import org.junit.Assert.assertEquals
import org.mockito.Mockito.mock
import org.robolectric.annotation.Config
import javax.inject.Inject
import kotlin.math.abs

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
class ActiveWorkoutViewModelTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var appDatabase: AppDatabase
    @Inject
    lateinit var locationRepository: LocationRepository
    lateinit var context: Context
    private lateinit var viewModel: ActiveWorkoutViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        context = mock(Context::class.java)
        viewModel = ActiveWorkoutViewModel(appDatabase, locationRepository = locationRepository, context = context)
    }

    @Test
    fun `Calculating the distance by gps location points`() {

        val pointsList = mutableListOf(
            "50.830315, 19.202589", "50.836169, 19.203787"
        )

        val result = viewModel.calculateDistance(
            pointsList,
            workoutId = 0
        )

        val expectedValue = 650

        val tolerance = expectedValue * 0.05

        assertFuzzyEquals(expectedValue, result, tolerance)

    }

    @Test
    fun `Calculate amount of kcal burned`() {

        val result = viewModel.calculateKcal(
            speedKmPerHour = 9,
            userWeight = 70,
            time = 3600
        )

        val expectedValue = 630

        val tolerance = expectedValue * 0.05

        assertEquals(expectedValue, result)
    }


    private fun assertFuzzyEquals(expected: Int, actual: Double, tolerance: Double) {
        val difference = abs(expected - actual)
        if (difference > tolerance) {
            throw AssertionError("Expected: $expected, but got: $actual (tolerance: $tolerance)")
        }
    }
}
