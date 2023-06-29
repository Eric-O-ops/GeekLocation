package com.geektech.data.repositories.locationprovider

import android.content.Context
import android.location.LocationManager
import com.geektech.domain.repositories.locationprovider.LocProviderGPSRep
import javax.inject.Inject

class LocProviderGPSRepImpl @Inject constructor(
    private val context: Context,
): LocProviderGPSRep{

    private val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    override fun isGPSEnabled(): Boolean = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

}