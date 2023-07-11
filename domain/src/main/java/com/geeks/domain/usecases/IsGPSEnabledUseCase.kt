package com.geeks.domain.usecases

import com.geeks.domain.repositories.locationprovider.LocationProviderRep
import javax.inject.Inject

class IsGPSEnabledUseCase @Inject constructor(
    private val rep: LocationProviderRep
) {
    operator fun invoke() = rep.isGPSEnabled()
}