package com.geektech.data.repositories.locationprovider

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.geektech.domain.repositories.locationprovider.LocProviderNetworkRep
import javax.inject.Inject

class LocProviderNetworkRepImpl @Inject constructor(
    private val context: Context,
) : LocProviderNetworkRep {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    override fun isNetworkEnabled(): Boolean {
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
        return false
    }
}