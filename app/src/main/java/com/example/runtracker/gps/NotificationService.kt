package com.example.runtracker.gps

import dagger.hilt.android.AndroidEntryPoint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.runtracker.R
import com.example.runtracker.constants.Constants
import com.example.runtracker.constants.Constants.CHANNEL_ID
import com.example.runtracker.constants.Constants.NOTIFICATION_ID_WORKOUT

@AndroidEntryPoint
class NotificationService: Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val message = intent!!.getStringExtra("message").toString()
        Log.d("messagesasas", "sasas")
        pushNotification(message)


        return super.onStartCommand(intent, flags, startId)
    }

    private fun pushNotification(message: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_run)
            .setContentTitle("Your Workout...")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID_WORKOUT, builder.build())
        }

        startForeground(NOTIFICATION_ID_WORKOUT, builder.build())

    }
}