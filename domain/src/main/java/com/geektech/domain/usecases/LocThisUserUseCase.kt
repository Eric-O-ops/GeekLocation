package com.geektech.domain.usecases

import com.geektech.domain.modles.LocModel
import com.geektech.domain.repositories.location.LocThisUserRep
import javax.inject.Inject

class LocThisUserUseCase @Inject constructor(
    private val rep: LocThisUserRep
) {
    operator fun invoke(location: LocModel) = rep.updateLoc(location)
}