package com.geeks.domain.usecases

import com.geeks.domain.repositories.location.LocationRequestRep
import javax.inject.Inject

class StopLocationRequestUseCase @Inject constructor(
    private val rep: LocationRequestRep
) {

    operator fun invoke() = rep.stop()
}