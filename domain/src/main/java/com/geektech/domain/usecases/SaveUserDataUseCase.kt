package com.geektech.domain.usecases

import com.geektech.domain.repositories.signin.SaveUserDataRep
import javax.inject.Inject

class SaveUserDataUseCase @Inject constructor(
    private val repository: SaveUserDataRep
) {
    operator fun invoke(name: String) = repository.saveUserData(name,)
}