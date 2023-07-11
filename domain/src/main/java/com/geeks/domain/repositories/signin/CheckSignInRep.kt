package com.geeks.domain.repositories.signin

interface CheckSignInRep {

    fun checkSignIn(email: String, onSuccess:() -> Unit, onError:() -> Unit)
}