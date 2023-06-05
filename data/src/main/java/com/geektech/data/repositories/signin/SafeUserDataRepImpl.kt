package com.geektech.data.repositories.signin

import android.util.Log
import com.geektech.data.repositories.preferences.userdata.UserPreferencesData
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
        val docRef = firestore.collection("CID").document("id")
        docRef.get(Source.SERVER).addOnSuccessListener { document ->
            if (document != null) {

                val freeId = document.data!!["count"].toString().toInt()
                Log.d("TAG", "DocumentSnapshot data: ${document.data!!["count"]}")

                val count = hashMapOf("count" to freeId + 1)
                firestore.collection("CID").document("id").set(count)

                val user = hashMapOf(
                    Constants.Firebase.userId to freeId,
                    Constants.Firebase.nameOfUsernameField to name,
                    Constants.Firebase.nameOfLocationField to GeoPoint(0.0,0.0)
                )

                pref.userId = freeId
                pref.userName = name

                firestore.collection(Constants.Firebase.nameOfCollation).document(name)
                    .set(user)
                    .addOnSuccessListener {
                        Log.d("TAGGER", "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e ->
                        Log.w("TAGGER", "Error writing document", e) }

            } else { Log.d("TAG", "No such document") }

        }.addOnFailureListener { exception -> Log.d("TAG", "get failed with ", exception) }
    }
}