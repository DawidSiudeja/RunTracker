package com.example.runtracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.runtracker.constants.Constants.CHANNEL_ID
import com.example.runtracker.gps.NotificationService
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Application: Application()