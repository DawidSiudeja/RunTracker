package com.example.runtracker.presentation.screens.active_workout


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runtracker.constants.Constants
import com.example.runtracker.data.AppDatabase
import com.example.runtracker.domain.models.Workout
import com.example.runtracker.gps.LocationRepository
import com.example.runtracker.gps.NotificationService
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.*
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

@HiltViewModel
class ActiveWorkoutViewModel @Inject constructor(
    private val appDatabase: AppDatabase,
    private val locationRepository: LocationRepository,
    @ApplicationContext private val context: Context
): ViewModel(){
    private var workoutId = 0
    var locationData: LiveData<Pair<Double, Double>> = locationRepository.locationData

    private val _distance = MutableStateFlow(0.00)
    val distance = _distance.asStateFlow()
    private var distanceJob: Job? = null

    private val _timer = MutableStateFlow(0)
    val timer = _timer.asStateFlow()
    private var timerJob: Job? = null

    private val _minutesPerKm = MutableStateFlow(0.00)
    val minutesPerKm = _minutesPerKm.asStateFlow()
    private var minutesPerKmJob: Job? = null

    private val _location = MutableStateFlow(0L)
    val location = _location.asStateFlow()
    private var locationJob: Job? = null

    private var notifyJob: Job? = null

    init {
        startWorkout()

    }


    fun getAllWorkouts(): Flow<List<Workout>> {
        return appDatabase.workoutDao().getAllWorkouts()
    }

    private fun startWorkout() {
        viewModelScope.launch {
            appDatabase.workoutDao().addWorkout(Workout(isActive = true))
            workoutId = appDatabase.workoutDao().getAllWorkouts().first().size
            startTimer()
            startObserveLocation(locationData, workoutId)
            startNotifyService()
        }
    }

    fun endWorkout() {
        viewModelScope.launch {
            val id = appDatabase.workoutDao().getAllWorkouts().first().size
            val speedKmPerHour = calculateAvgSpeedKmH(distance.value, timer.value)
            val userWeight = appDatabase.userInfoDao().getAllUserInfo().first()[0].userWeight
            val kcalBurned = calculateKcal(speedKmPerHour, userWeight, timer.value)

            appDatabase.workoutDao().setActiveOfWorkout(isActive = false, id = id)
            appDatabase.workoutDao().updateTimeOfWorkout(timer.value, id)
            appDatabase.workoutDao().updateDistance(distance.value, id)
            appDatabase.workoutDao().updateMinutesPerKmOfWorkout(minutesPerKm.value ,id)
            appDatabase.workoutDao().updateAvgSpeedOfWorkout(speedKmPerHour, id)
            appDatabase.workoutDao().updateKcal(kcalBurned, id)
            stopNotifyService()
            stopMinutesPerKm()
            stopDistance()
            stopTimer()
            stopObserveLocation()
        }
    }

    fun calculateDistance(pointList: MutableList<String>, workoutId: Int): Double {

        val earthRadius = 6371.0
        var i = pointList.size - 1
        Log.d("WORKOUT", pointList.size.toString())

        if (pointList.size >= 2) {

            val firstLocation = pointList[i].split(",")

            val firstLongitude = firstLocation[0].trim().toDouble()
            val firstLatitude = firstLocation[1].trim().toDouble()


            val secondLocation = pointList[i - 1].split(",")

            val secondLongitude = secondLocation[0].trim().toDouble()
            val secondLatitude = secondLocation[1].trim().toDouble()

            val dLat = Math.toRadians(secondLatitude - firstLatitude)
            val dLon = Math.toRadians(secondLongitude - firstLongitude)

            val lat1 = Math.toRadians(firstLatitude)
            val lat2 = Math.toRadians(secondLongitude)


            val a = sin(dLat / 2) * sin(dLat / 2) +
                    cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                    sin(dLon / 2) * sin(dLon / 2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            val segmentDistance = earthRadius * c * 1000 // Distance in meter

            Log.d("WORKOUT", "DISTANCE: $segmentDistance")

            viewModelScope.launch {
                val currentDistance = appDatabase.workoutDao()
                    .getSpecifWorkout(workoutId).first().distance
                var distance = currentDistance + segmentDistance

                appDatabase.workoutDao().updateDistance(distance, workoutId)

            }
            return segmentDistance

        }
        return 0.0
    }

    private fun calculateMinutesPerKm(distance: Double, timerValue: Int): Double {
        val distanceInKm = distance / 1000.0
        val timeInMinutes = timerValue.toDouble() / 60.0
        val minutesPerKm = timeInMinutes / distanceInKm
        return minutesPerKm
    }

    private fun calculateAvgSpeedKmH(distance: Double, time: Int): Int {
        Log.d("speed", ((distance / 1000.0) / (time / 60.0 / 60.0)).roundToInt().toString())
        return ((distance / 1000.0) / (time / 60.0 / 60.0)).roundToInt()
    }

    private fun calculateMet(speedKmPerHour: Int): Int {
        val metValue = when {
            speedKmPerHour == 0 -> 0
            speedKmPerHour < 3 -> 2 // przechadzka // Slow walk
            speedKmPerHour < 5 -> 3 // spacer // Medium walk
            speedKmPerHour < 6 -> 4 // szybki spacer // High walk
            speedKmPerHour < 8 -> 7 // wolny bieg // Slow run
            speedKmPerHour < 10 -> 9 // średni bieg // Medium run
            else -> 12 // szybki bieg // High run
        }
        return metValue
    }

    fun calculateKcal(
        speedKmPerHour: Int,
        userWeight: Int,
        time: Int
    ): Int {
        val met = calculateMet(speedKmPerHour)
        Log.d("meta", met.toString())
        Log.d("meta", userWeight.toString())
        Log.d("meta", time.toString())
        Log.d("meta", (met * userWeight * (time / 3600.0)).toString())
        return (met * userWeight * (time / 3600.0)).toInt()
    }



    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _timer.value++
            }
        }
    }

    fun startObserveLocation(location: LiveData<Pair<Double, Double>>, workoutId: Int) {
        locationJob?.cancel()
        locationJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                val currentLocation = location.value
                if (currentLocation != null) {
                    val pointList = "${currentLocation.first},${currentLocation.second}"
                    val workout = appDatabase.workoutDao().getSpecifWorkout(workoutId).first()

                    val updatedPointsList = mutableListOf<String>()

                    if(workout.points.isNotEmpty()) {
                        updatedPointsList.addAll(workout.points)
                    }

                    updatedPointsList.add(pointList)
                    val distanceSegment = calculateDistance(updatedPointsList, workoutId)
                    startDistance(distanceSegment)

                    if(distance.value > 0) {
                        startMinutesPerKm(distance.value, timer.value)
                    }

                    appDatabase.workoutDao().updateListPointsOfWorkout(updatedPointsList, workoutId)

                }
            }
        }
    }


    fun stopObserveLocation() {
        locationJob?.cancel()
    }

    fun startMinutesPerKm(distanceValue: Double, timerValue: Int) {
        minutesPerKmJob?.cancel()
        minutesPerKmJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                withContext(Dispatchers.Main) {
                    val newMinuterPerKmValue = calculateMinutesPerKm(distanceValue, timerValue)
                    _minutesPerKm.value = newMinuterPerKmValue
                }
            }
        }
    }

    fun stopMinutesPerKm() {
        minutesPerKmJob?.cancel()
    }
    fun startDistance(distanceValue: Double) {
        distanceJob?.cancel()
        distanceJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                withContext(Dispatchers.Main) {
                    _distance.value += distanceValue
                }
            }
        }
    }

    fun stopDistance() {
        distanceJob?.cancel()
    }

    fun pauseTimer() {
        timerJob?.cancel()
    }

    fun stopTimer() {
        _timer.value = 0
        timerJob?.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        distanceJob?.cancel()
    }

    fun startNotifyService() {
        notifyJob?.cancel()
        notifyJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                withContext(Dispatchers.Main) {

                    val timeNotify = formatTime(timer.value)
                    val distanceNotify = String.format("%.1f", distance.value / 1000)

                    val notificationManager = getApplication(context.applicationContext)
                        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                    val channel = NotificationChannel(
                        Constants.CHANNEL_ID,
                        "Location",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )

                    notificationManager.createNotificationChannel(channel)

                    val serviceIntent2 = Intent(getApplication(context.applicationContext), NotificationService::class.java)
                    serviceIntent2.putExtra("message",
                        "Duration: $timeNotify \n" +
                                "Distance: $distanceNotify km")
                    ContextCompat.startForegroundService(getApplication(context.applicationContext), serviceIntent2)
                }
            }
        }

    }

    fun stopNotifyService() {
        notifyJob?.cancel()
    }

    fun formatTime(timeToFormat: Int): String {
        val hours = timeToFormat / 3600
        val minutes = (timeToFormat % 3600) / 60
        val remainingSeconds = timeToFormat % 60
        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    }
}
