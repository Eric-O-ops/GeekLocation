package com.geektech.data.repositories.signin

import android.util.Log
import com.geektech.data.preferences.userdata.UserPreferencesData
import com.geektech.domain.base.constansts.Constants
import com.geektech.domain.repositories.signin.SaveUserDataRep
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Source
import javax.inject.Inject

class SafeUserDataRepImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val pref: UserPreferencesData,
    private val auth: FirebaseAuth
) : SaveUserDataRep {

    override fun saveUserData(name: String) {

        firestore.collection(Constants.FirebaseCID.NAME_COLLECTION)
            .document(Constants.FirebaseCID.NAME_DOC)
            .get(Source.SERVER).addOnSuccessListener { document ->
                if (document != null) {
                    firestore.collection(Constants.FirebaseUsers.NAME_COLLECTION)
                        .document(getUID())
                        .set(setUpUser(name, document))
                        .addOnSuccessListener {
                            Log.d("TAGGER", "DocumentSnapshot successfully written!")
                        }
                        .addOnFailureListener { e ->
                            Log.w("TAGGER", "Error writing document", e)
                        }

                } else { Log.d("TAG", "No such document") }

            }.addOnFailureListener { exception -> Log.d("TAG", "get failed with ", exception) }
    }

    private fun getId(doc: DocumentSnapshot): Int {
        val userId = doc.data!![Constants.FirebaseCID.NAME_FIELD].toString().toInt()
        val freeId = hashMapOf(Constants.FirebaseCID.NAME_FIELD to userId + 1)
        firestore
            .collection(Constants.FirebaseCID.NAME_COLLECTION)
            .document(Constants.FirebaseCID.NAME_DOC).set(freeId)
        pref.userId = userId
        return userId
    }

    private fun setUpUser(
        name: String,
        doc: DocumentSnapshot
    ): HashMap<String, Comparable<*>> {
        val email = auth.currentUser?.email ?: "no email" //todo
        safeUserAccountId()
        return hashMapOf(
            Constants.FirebaseUsers.USER_ID_FIELD to getId(doc),
            Constants.FirebaseUsers.USER_ACCOUNT_ID to getUID(),
            Constants.FirebaseUsers.USER_EMAIL to email,
            Constants.FirebaseUsers.USER_NAME_FIELD to name,
            Constants.FirebaseUsers.LOC_FIELD to GeoPoint(0.0, 0.0)
        )
    }

    private fun safeUserAccountId() { pref.userAccountId = getUID() }

    private fun getUID(): String = auth.currentUser?.uid ?: "no uid"  //todo
}