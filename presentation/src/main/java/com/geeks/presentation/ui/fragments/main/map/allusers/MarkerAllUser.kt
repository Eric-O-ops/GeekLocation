package com.geeks.presentation.ui.fragments.main.map.allusers

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.geeks.presentation.models.LocModelUI
import com.geeks.presentation.R
import com.geeks.presentation.ui.fragments.main.map.ConvertToBitmap
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * This class display and update information about users
 *
 * "display()" for add and update location on map
 * "zoomChanged()" for update icon of markers when zoom <= 19
 *
 * In constructor of the class add only application context in "applicationContext"
 * */

class MarkerAllUser(
    private val context: Context,
    private val applicationContext: Context
) {
    private var usersMarker: ArrayList<Marker> = ArrayList()
    private val convertImage = ConvertToBitmap.Base()

    // todo improve
    fun display(usersOnDB: ArrayList<LocModelUI>, mMap: GoogleMap) {

       /* val buildMarker = fun () {}*/
        when {
            usersMarker.isEmpty() -> {
                buildUserMarker(usersOnDB, mMap)
            }
            usersOnDB.size > usersMarker.size -> {
                addNewUserMarker(usersOnDB, mMap)
            }
            else -> {
                updateUserLocation(usersOnDB, mMap)
            }
        }
    }

    private fun buildUserMarker(usersOnDB: ArrayList<LocModelUI>, mMap: GoogleMap) {
        for (value in usersOnDB) {
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(value.latitude, value.longitude))
                    .title(value.name)
                    .icon(convertImage.convert(context, R.drawable.marker_other_user))
            )
            marker?.let { usersMarker.add(it) }
        }
    }

    private fun addNewUserMarker(usersOnDB: ArrayList<LocModelUI>, mMap: GoogleMap) {
        val users = usersOnDB.size - usersMarker.size
        val newUsersList: ArrayList<LocModelUI> = ArrayList()
        for (user in users - 1 until usersOnDB.size) { newUsersList.add(usersOnDB[user]) }
        buildUserMarker(newUsersList, mMap)
    }

    private fun updateUserLocation(usersOnDB: ArrayList<LocModelUI>, mMap: GoogleMap) {
        try {
            for ((i, value) in usersOnDB.withIndex()) {
                val position = LatLng(value.latitude, value.longitude)
                usersMarker[i].position = position
            }

        } catch (e: Exception) {
            Log.e("NPE", e.toString())
            usersMarker.removeAll(usersMarker.toSet())
            Log.e("NPE", "usersMarkerTest was cleared")
            buildUserMarker(usersOnDB,mMap)
        }
    }

    @SuppressLint("InflateParams")
    //todo come up with new name for
     fun zoomChanged(zoom: Float) {

        val marker = LayoutInflater.from(context).inflate(R.layout.marker_other_person, null)
        val container = marker.findViewById<LinearLayout>(R.id.container_marker)
        val textUnderIcon = marker.findViewById<TextView>(R.id.user_name)

        fun addTextUnderIcon(marker: Marker) {
            marker.setIcon(
                ConvertToBitmap.Base().convert(
                    applicationContext,
                    R.drawable.marker_other_user
                )
            )
        }

        fun removeTextUnderIcon(marker: Marker) {
            marker.setIcon(
                BitmapDescriptorFactory.
                fromBitmap(ConvertToBitmap.Base().convert(container))
            )
        }

        usersMarker.forEach {
            textUnderIcon.text = it.title
            if (zoom <= 19) addTextUnderIcon(it) else removeTextUnderIcon(it)
        }
    }
}