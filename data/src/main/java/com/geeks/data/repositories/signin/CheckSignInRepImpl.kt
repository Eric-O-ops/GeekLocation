package com.geeks.data.repositories.signin

import android.util.Log
import com.geeks.data.preferences.userdata.UserPreferencesData
import com.geeks.domain.base.constansts.Constants
import com.geeks.domain.repositories.signin.CheckSignInRep
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CheckSignInRepImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val pref: UserPreferencesData
) : CheckSignInRep {

    override fun checkSignIn(token: String, onSuccess: () -> Unit, onError: () -> Unit) {
        val firebaseCredential = GoogleAuthProvider.getCredential(token, null)
        val collections = firestore.collection(Constants.FirebaseUsers.NAME_COLLECTION)
        auth.signInWithCredential(firebaseCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser!!.email?.let { email ->
                        collections.addSnapshotListener { value, e ->
                            if (e != null) {
                                Log.w("TAGGER", "Listen failed.", e)
                                return@addSnapshotListener
                            }

                            for (doc in value!!) {
                                if (doc.getString("email") == email) {
                                    pref.userAccountId = auth.currentUser?.uid ?: "no id" //todo
                                    onSuccess()
                                    return@addSnapshotListener
                                }
                            }
                            onError()
                        }
                    }
                }
            }
    }
}