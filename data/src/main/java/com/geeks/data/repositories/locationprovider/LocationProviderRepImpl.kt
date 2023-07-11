package com.geeks.data.repositories.locationprovider

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.geeks.domain.repositories.locationprovider.LocationProviderRep
import javax.inject.Inject

class LocationProviderRepImpl @Inject constructor(
    private val context: Context
) : LocationProviderRep {

    private val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    override fun isGPSEnabled(): Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

    override fun isNetworkEnabled(): Boolean {
        if (capabilities != null) {
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { true }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { true }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> { true }
                else -> {false}
            }
        }
        return false
    }
}