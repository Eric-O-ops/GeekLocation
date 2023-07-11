package com.geeks.data.repositories.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.geeks.data.models.LocModelData
import com.geeks.data.models.toDomain
import com.geeks.domain.models.LocModelDomain
import com.geeks.domain.repositories.location.LocationRequestRep
import com.google.android.gms.location.*
import javax.inject.Inject

class LocationRequestRepImpl @Inject constructor(
    private val context: Context
) : LocationRequestRep {

    private val fusedLocClient = LocationServices.getFusedLocationProviderClient(context)
    private var locCallback: LocationCallback? = null

    @SuppressLint("MissingPermission")
    override fun start(location: (LocModelDomain) -> Unit) {

        val locRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 500)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(2000)
            .setMaxUpdateDelayMillis(500)
            .build()

        locCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val loc = locationResult.lastLocation!!
                location(LocModelData(null,loc.latitude,loc.longitude, loc.bearing).toDomain())
              //  Log.e("SERVICEGOGO", "onLocationResult: $loc")
            }
        }

        fusedLocClient.requestLocationUpdates(
            locRequest,
            locCallback!!,
            Looper.getMainLooper()
        )
    }

    override fun stop() {
        locCallback?.let {
            fusedLocClient.removeLocationUpdates(it)
        }
    }
}