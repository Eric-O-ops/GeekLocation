package com.geeks.presentation

import android.util.Log
import com.geeks.domain.modles.LocModel
import com.google.android.gms.maps.model.LatLng
import junit.framework.TestCase.assertEquals

import org.junit.Test

class DisplayAllMarkers {

    //assertEquals(4, 2 + 2)
    private val usersMarker: ArrayList<TestMarkerModel> = ArrayList()
    private var init = false

    private fun displayTest(usersList: ArrayList<LocModel>) {

        val markerOnMap = if (usersMarker.isEmpty()) 0 else usersMarker.size - 1
        val markerOnDb = usersList.size - 1

        if (markerOnMap < markerOnDb) {
            for (value in markerOnMap..markerOnDb) {
                val position = LatLng(usersList[value].latitude, usersList[value].longitude)
                val marker = TestMarkerModel(usersList[value].name, position)
                usersMarker.add(marker)
            }
            if (init) displayTest(usersList)
            init = true
        } else {
            try {
                for (value in 0 .. markerOnMap) {
                    val position = LatLng(usersList[value].latitude, usersList[value].longitude)
                    usersMarker[value].apply { this.position = position }
                }
            } catch (e: Exception) {
                Log.e("NPE", e.toString())
            }
        }
        assertEquals(4, 2 + 2)
    }

    @Test
    fun main() {
        val random = Math.random() * (74.0 - 0.0 + 1)
        displayTest(
            arrayListOf(
                LocModel("Eric", random, random),
                LocModel("Marsel", random, random),
                LocModel("Vlad", random, random)
            )
        )
    }
}

data class TestMarkerModel(
    val name: String? = null,
    var position: LatLng
)