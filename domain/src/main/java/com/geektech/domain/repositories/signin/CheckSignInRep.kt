package com.geektech.domain.repositories.signin

interface CheckSignInRep {

    fun checkSignIn(token: String, onSuccess:() -> Unit, onError:() -> Unit)
}