package com.geektech.domain.repositories.signin

interface SignInRep {

    fun firebaseWithOneTap(idToken: String, onSuccess: () -> Unit, onError: () -> Unit)
}