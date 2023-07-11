package com.geeks.domain.usecases

import com.geeks.domain.repositories.signin.CheckSignInRep
import javax.inject.Inject

class CheckSignInUseCase @Inject constructor(
    private val rep: CheckSignInRep
) {
    operator fun invoke(email: String, onSuccess: () -> Unit, onError: () -> Unit) =
        rep.checkSignIn(email, onSuccess, onError)
}