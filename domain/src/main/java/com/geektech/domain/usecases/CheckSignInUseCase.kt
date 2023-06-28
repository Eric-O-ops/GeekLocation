package com.geektech.domain.usecases

import com.geektech.domain.repositories.signin.CheckSignInRep
import javax.inject.Inject

class CheckSignInUseCase @Inject constructor(
    private val rep: CheckSignInRep
) {
    operator fun invoke(token: String, onSuccess: () -> Unit, onError: () -> Unit) =
        rep.checkSignIn(token, onSuccess, onError)
}