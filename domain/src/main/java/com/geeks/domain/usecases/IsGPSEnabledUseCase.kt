package com.geeks.domain.usecases

import com.geeks.domain.repositories.locationprovider.LocProviderGPSRep
import javax.inject.Inject

class IsGPSEnabledUseCase @Inject constructor(
    private val rep: LocProviderGPSRep
) {
    operator fun invoke() = rep.isGPSEnabled()
}