package com.geeks.domain.repositories.signin

interface SignInRep {

    fun firebaseWithOneTap(idToken: String, onSuccess: () -> Unit, onError: () -> Unit)
}