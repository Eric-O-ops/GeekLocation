package com.geeks.domain.usecases

import com.geeks.domain.repositories.signin.SignInRep
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: SignInRep
) {

    operator fun invoke(idToken: String, onSuccess: () -> Unit, onError: () -> Unit) =
        repository.firebaseWithOneTap(idToken, onSuccess, onError)
}