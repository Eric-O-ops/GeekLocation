package com.geektech.data.repositories.location

import com.geektech.data.repositories.preferences.userdata.UserPreferencesData
import com.geektech.domain.base.constansts.Constants
import com.geektech.domain.modles.LocModel
import com.geektech.domain.repositories.location.LocThisUserRep
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import javax.inject.Inject

class LocThisUserRepImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val pref: UserPreferencesData
) : LocThisUserRep {

    override fun updateLoc(location: LocModel) {

        db.collection(Constants.Firebase.nameOfCollation).document(pref.userName)
            .update(
                Constants.Firebase.nameOfLocationField,
                GeoPoint(location.latitude, location.longitude)
            )
    }
}