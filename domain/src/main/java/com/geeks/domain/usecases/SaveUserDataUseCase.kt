package com.geeks.domain.usecases

import com.geeks.domain.repositories.signin.SaveUserDataRep
import javax.inject.Inject

class SaveUserDataUseCase @Inject constructor(
    private val repository: SaveUserDataRep
) {
    operator fun invoke(name: String) = repository.saveUserData(name,)
}