package com.geeks.presentation.ui.fragments.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geeks.domain.base.constansts.Constants
import com.geeks.domain.modles.LocModel
import com.geeks.domain.usecases.IsGPSEnabledUseCase
import com.geeks.domain.usecases.IsNetworkEnabledUseCase
import com.geeks.domain.usecases.LocThisUserUseCase
import com.geeks.domain.usecases.PreferenceNameUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val locThisUserUseCase: LocThisUserUseCase,
    private val prefNameUseCase: PreferenceNameUseCase,
    private val gpsEnabledUseCase: IsGPSEnabledUseCase,
    private val networkEnabledUseCase: IsNetworkEnabledUseCase
): ViewModel() {

    private var _users: MutableLiveData<ArrayList<LocModel>> = MutableLiveData()

    fun updateLoc(location: LocModel) = locThisUserUseCase(location)

    fun fetchUsers(): MutableLiveData<ArrayList<LocModel>> {
        db.collection(Constants.FirebaseUsers.NAME_COLLECTION)
            .orderBy(Constants.FirebaseUsers.USER_ID_FIELD)
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("TAGGER", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val allUsers: ArrayList<LocModel> = ArrayList()
                for (doc in value!!) {
                    val geoPoint = doc.getGeoPoint(Constants.FirebaseUsers.LOC_FIELD)!!
                    if(doc.getString(Constants.FirebaseUsers.USER_ACCOUNT_ID)
                        != prefNameUseCase()) {
                        allUsers.add(
                            LocModel(
                                doc.getString(Constants.FirebaseUsers.USER_NAME_FIELD)!!,
                                geoPoint.latitude,
                                geoPoint.longitude
                            )
                        )
                    }
                }
                _users.value = allUsers
            }
        return _users
    }

    fun isGpsEnabled() = gpsEnabledUseCase()

    fun isNetworkEnable() = networkEnabledUseCase()
}