package com.geektech.data.repositories.signin

import android.util.Log
import com.geektech.data.preferences.userdata.UserPreferencesData
import com.geektech.domain.base.constansts.Constants
import com.geektech.domain.repositories.signin.SaveUserDataRep
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Source
import javax.inject.Inject

class SafeUserDataRepImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val pref: UserPreferencesData
) : SaveUserDataRep {

    override fun saveUserData(name: String) {

        val docRef = firestore.collection(Constants.FirebaseCID.NAME_COLLECTION)
            .document(Constants.FirebaseCID.NAME_DOC)
        docRef.get(Source.SERVER).addOnSuccessListener { document ->

            if (document != null) {

                val freeId = document.data!![Constants.FirebaseCID.NAME_FIELD].toString().toInt()
                Log.d(
                    "TAG",
                    "DocumentSnapshot data: ${document.data!![Constants.FirebaseCID.NAME_FIELD]}"
                )

                val count = hashMapOf(Constants.FirebaseCID.NAME_FIELD to freeId + 1)
                firestore
                    .collection(Constants.FirebaseCID.NAME_COLLECTION)
                    .document(Constants.FirebaseCID.NAME_FIELD).set(count)

                val user = hashMapOf(
                    Constants.FirebaseUsers.USER_ID_FIELD to freeId,
                    Constants.FirebaseUsers.USER_NAME_FIELD to name,
                    Constants.FirebaseUsers.LOC_FIELD to GeoPoint(0.0, 0.0)
                )

                pref.userId = freeId
                pref.userName = name

                firestore.collection(Constants.FirebaseUsers.NAME_COLLECTION).document(name)
                    .set(user)
                    .addOnSuccessListener {
                        Log.d("TAGGER", "DocumentSnapshot successfully written!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("TAGGER", "Error writing document", e)
                    }

            } else { Log.d("TAG", "No such document") }

        }.addOnFailureListener { exception -> Log.d("TAG", "get failed with ", exception) }
    }
}