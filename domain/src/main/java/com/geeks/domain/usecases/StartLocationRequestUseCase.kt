package com.geeks.domain.usecases

import com.geeks.domain.models.LocModelDomain
import com.geeks.domain.repositories.location.LocationRequestRep
import javax.inject.Inject

class StartLocationRequestUseCase @Inject constructor(
    private val rep: LocationRequestRep
) {

    operator fun invoke(location:(LocModelDomain) -> Unit) = rep.start(location)
}