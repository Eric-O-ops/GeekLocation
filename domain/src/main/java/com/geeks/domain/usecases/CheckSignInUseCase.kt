package com.geeks.domain.usecases

import com.geeks.domain.repositories.signin.CheckSignInRep
import javax.inject.Inject

class CheckSignInUseCase @Inject constructor(
    private val rep: CheckSignInRep
) {
    operator fun invoke(token: String, onSuccess: () -> Unit, onError: () -> Unit) =
        rep.checkSignIn(token, onSuccess, onError)
}