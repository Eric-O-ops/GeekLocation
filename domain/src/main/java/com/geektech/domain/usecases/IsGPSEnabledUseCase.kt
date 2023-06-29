package com.geektech.domain.usecases

import com.geektech.domain.repositories.locationprovider.LocProviderGPSRep
import javax.inject.Inject

class IsGPSEnabledUseCase @Inject constructor(
    private val rep: LocProviderGPSRep
) {
    operator fun invoke() = rep.isGPSEnabled()
}