package com.geeks.domain.usecases

import com.geeks.domain.repositories.locationprovider.LocProviderNetworkRep
import javax.inject.Inject

class IsNetworkEnabledUseCase @Inject constructor(
    private val rep: LocProviderNetworkRep
) {

    operator fun invoke() = rep.isNetworkEnabled()
}