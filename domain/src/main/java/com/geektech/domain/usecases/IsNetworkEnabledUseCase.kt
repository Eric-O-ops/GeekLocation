package com.geektech.domain.usecases

import com.geektech.domain.repositories.locationprovider.LocProviderNetworkRep
import javax.inject.Inject

class IsNetworkEnabledUseCase @Inject constructor(
    private val rep: LocProviderNetworkRep
) {

    operator fun invoke() = rep.isNetworkEnabled()
}