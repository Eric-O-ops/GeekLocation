package com.geeks.data.repositories.signin

import com.geeks.data.preferences.userdata.UserPreferencesData
import com.geeks.domain.repositories.signin.CheckSignInRep
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CheckSignInRepImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val pref: UserPreferencesData
) : CheckSignInRep {

    override fun checkSignIn(userEmail: String, onSuccess: () -> Unit, onError: () -> Unit) {
        firestore.collection("Users")
            .get()
            .addOnSuccessListener {
                var noEmail = true
                for (doc in it) {
                    val email = doc.getString("email")
                    if (email == userEmail) {
                        pref.userEmail = email
                        onSuccess()
                        noEmail = false
                        break
                    }
                    noEmail = true
                }
                if (noEmail) onError()
            }
    }
}