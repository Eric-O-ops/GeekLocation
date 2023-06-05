package com.geektech.presentation.ui.fragments.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geektech.domain.modles.LocModel
import com.geektech.domain.usecases.LocThisUserUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locThisUserUseCase: LocThisUserUseCase,
    private val db: FirebaseFirestore
): ViewModel() {

    private var _users: MutableLiveData<ArrayList<LocModel>> = MutableLiveData()

    fun updateLoc(location: LocModel) = locThisUserUseCase(location)

    fun fetchUsers(): MutableLiveData<ArrayList<LocModel>> {
        db.collection("Users")
            .orderBy("id")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w("TAGGER", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val allUsers: ArrayList<LocModel> = ArrayList()
                for (doc in value!!) {
                    val geoPoint = doc.getGeoPoint("location")!!
                    if(doc.getString("name") != "Eric") {
                        allUsers.add(
                            LocModel(
                                doc.getString("name")!!,
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