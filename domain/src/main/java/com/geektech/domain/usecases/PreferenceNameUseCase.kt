package com.geektech.domain.usecases

import com.geektech.domain.repositories.preference.PreferenceRep
import javax.inject.Inject

class PreferenceNameUseCase @Inject constructor(
    private val rep: PreferenceRep
) {

    operator fun invoke() = rep.getUserName()
}