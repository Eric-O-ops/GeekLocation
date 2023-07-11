package com.geeks.presentation.ui.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Looper
import android.util.Log
import com.geeks.presentation.R
import androidx.core.app.NotificationCompat
import com.geeks.domain.base.constansts.Constants
import com.geeks.presentation.models.LocModelUI
import com.geeks.domain.usecases.LocThisUserUseCase
import com.geeks.presentation.models.toDomain
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    @Inject lateinit var locThisUserUseCase: LocThisUserUseCase
    private val scope = CoroutineScope(Dispatchers.IO + Job())

    private var fusedLocClient: FusedLocationProviderClient? = null
    private var locCallback: LocationCallback? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("SERVICEGOGO", "on start command")
        if (!SERVICE_RUNNING) {
            scope.launch(CoroutineName("foreground location service")) {
                setNotification()
                startTracking()
                SERVICE_RUNNING = true
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_ID,
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

            val notification: Notification.Builder = Notification.Builder(this, CHANNEL_ID)
                .setContentText(Constants.Notification.FOREGROUND_CONTENT_TEXT)
                .setContentTitle(Constants.Notification.FOREGROUND_CONTENT_TITLE)
                .setSmallIcon(R.drawable.ic_location_notification)

            startForeground(NOTIFICATION_ID, notification.build())
        } else {
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText(Constants.Notification.FOREGROUND_CONTENT_TEXT)
                .setContentTitle(Constants.Notification.FOREGROUND_CONTENT_TITLE)
                .setSmallIcon(R.drawable.ic_location_notification)

            startForeground(NOTIFICATION_ID, notification.build())
        }
    }

    @SuppressLint("MissingPermission")
    private fun startTracking() {

        fusedLocClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        val locRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 500)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(2000)
            .setMaxUpdateDelayMillis(1000)
            .build()

        locCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation!!
                locThisUserUseCase(
                    LocModelUI(
                        null,
                        location.latitude,
                        location.longitude,
                        location.bearing
                    ).toDomain()!!
                )
                Log.e("SERVICEGOGO", "onLocationResult: $location")
            }
        }

        fusedLocClient?.requestLocationUpdates(
            locRequest,
            locCallback!!,
            Looper.getMainLooper()
        )
    }

    override fun onBind(intent: Intent?) = null

    override fun onDestroy() {
        super.onDestroy()
        Log.e("SERVICEGOGO", "on destroy")
        fusedLocClient?.removeLocationUpdates(locCallback!!)
        SERVICE_RUNNING = false
    }

    companion object {
        const val CHANNEL_ID = "Foreground Service ID"
        const val NOTIFICATION_ID = 1001
        @JvmStatic var SERVICE_RUNNING = false // todo
    }
}