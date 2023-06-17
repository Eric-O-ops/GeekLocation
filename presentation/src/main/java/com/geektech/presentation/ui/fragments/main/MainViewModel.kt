package com.geektech.presentation.ui.fragments.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geektech.domain.base.constansts.Constants
import com.geektech.domain.modles.LocModel
import com.geektech.domain.usecases.LocThisUserUseCase
import com.geektech.domain.usecases.PreferenceNameUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db: FirebaseFirestore,
    private val locThisUserUseCase: LocThisUserUseCase,
    private val prefNameUseCase: PreferenceNameUseCase
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
                    if(doc.getString(Constants.FirebaseUsers.USER_NAME_FIELD)
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
}