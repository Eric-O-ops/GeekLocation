package com.geeks.data.repositories.location

import android.util.Log
import com.geeks.data.preferences.userdata.UserPreferencesData
import com.geeks.domain.base.constansts.Constants
import com.geeks.domain.models.LocModelDomain
import com.geeks.domain.repositories.location.LocThisUserRep
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import javax.inject.Inject

class LocThisUserRepImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val pref: UserPreferencesData
) : LocThisUserRep {

    override fun updateLoc(location: LocModelDomain) {

        Log.e("GOGO", pref.userEmail )

        db.collection(Constants.FirebaseUsers.NAME_COLLECTION)
            .document(pref.userEmail)
            .update(
                Constants.FirebaseUsers.LOC_FIELD,
                GeoPoint(location.latitude, location.longitude)
            )
    }
}