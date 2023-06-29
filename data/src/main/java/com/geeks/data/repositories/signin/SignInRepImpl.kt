package com.geeks.data.repositories.signin

import com.geeks.domain.repositories.signin.SignInRep
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class SignInRepImpl @Inject constructor(
    private val auth: FirebaseAuth
) : SignInRep {

    override fun firebaseWithOneTap(idToken: String, onSuccess: () -> Unit, onError: () -> Unit) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task -> if (task.isSuccessful) onSuccess() else onError() }
    }
}