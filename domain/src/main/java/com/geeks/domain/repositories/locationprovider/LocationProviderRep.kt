package com.geeks.domain.repositories.locationprovider

interface LocationProviderRep {

    fun isGPSEnabled(): Boolean

    fun isNetworkEnabled(): Boolean
}