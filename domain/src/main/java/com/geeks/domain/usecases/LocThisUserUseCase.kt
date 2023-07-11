package com.geeks.domain.usecases

import com.geeks.domain.models.LocModelDomain
import com.geeks.domain.repositories.location.LocThisUserRep
import javax.inject.Inject

class LocThisUserUseCase @Inject constructor(
    private val rep: LocThisUserRep
) {
    operator fun invoke(location: LocModelDomain) = rep.updateLoc(location)
}