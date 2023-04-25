package com.geektech.domain.repositories

interface SignInRep {

    fun firebaseWithOneTap(idToken: String, onSuccess: () -> Unit, onError: () -> Unit)
}