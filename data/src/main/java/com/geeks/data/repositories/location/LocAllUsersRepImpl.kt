package com.geeks.data.repositories.location

import android.util.Log
import com.geeks.data.models.LocModelData
import com.geeks.data.models.toDomain
import com.geeks.data.preferences.userdata.UserPreferencesData
import com.geeks.domain.base.constansts.Constants
import com.geeks.domain.models.LocModelDomain
import com.geeks.domain.repositories.location.LocAllUsersRep
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class LocAllUsersRepImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val pref: UserPreferencesData
) : LocAllUsersRep {

    private val allUsers: ArrayList<LocModelDomain> = ArrayList()

    override fun fetchUsers(list: (ArrayList<LocModelDomain>) -> Unit) {
        firestore.collection(Constants.FirebaseUsers.NAME_COLLECTION)
            .orderBy(Constants.FirebaseUsers.USER_ID_FIELD)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("TAGGER", "Listen failed.", e)
                    return@addSnapshotListener
                }
                //todo NPE
                for (doc in value!!) {
                    val geoPoint = doc.getGeoPoint(Constants.FirebaseUsers.LOC_FIELD)!!
                    val email = doc.getString(Constants.FirebaseUsers.USER_EMAIL)
                    if (email != pref.userEmail) {
                        allUsers.add(
                            LocModelData(
                                doc.getString(Constants.FirebaseUsers.USER_NAME_FIELD)!!,
                                geoPoint.latitude,
                                geoPoint.longitude,
                                0F
                            ).toDomain()
                        )
                    }
                }
                list(allUsers)
            }
    }
}