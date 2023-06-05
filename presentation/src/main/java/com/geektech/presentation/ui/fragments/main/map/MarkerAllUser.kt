package com.geektech.presentation.ui.fragments.main.map

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.geektech.domain.modles.LocModel
import com.geektech.presentation.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.delay

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
    private var init = false

    fun display(usersList: ArrayList<LocModel>, mMap: GoogleMap) {
        val markerOnMap = if (usersMarker.isEmpty()) 0 else usersMarker.size - 1
        val markerOnDb = usersList.size - 1

        if (markerOnMap == 0) {
            for (value in 0..markerOnDb) {
                val position = LatLng(usersList[value].latitude, usersList[value].longitude)
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(usersList[value].name)
                        .icon(convertImage.convert(context, R.drawable.marker_other_user))
                )
                usersMarker.add(marker!!)
            }
            if (init) display(usersList, mMap)
            init = true
        } else if (markerOnMap < markerOnDb) {
            for (value in markerOnMap + 1..markerOnDb) {
                val position = LatLng(usersList[value].latitude, usersList[value].longitude)
                val marker = mMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(usersList[value].name)
                        .icon(convertImage.convert(context, R.drawable.marker_other_user))
                )
                usersMarker.add(marker!!)
            }
        } else {
            try {
                for (value in 0 .. markerOnMap) {
                    val position = LatLng(usersList[value].latitude, usersList[value].longitude)
                    usersMarker[value].apply { this.position = position }
                }
            } catch (e: Exception) { Log.e("NPE", e.toString()) }
        }
    }

    @SuppressLint("InflateParams")
    suspend fun zoomChanged(zoom: Float) {
        delay(2000L)
        val markerView =
            LayoutInflater.from(context).inflate(R.layout.marker_other_person, null)
        val container = markerView.findViewById<LinearLayout>(R.id.container_marker)
        val textUnderIcon = markerView.findViewById<TextView>(R.id.user_name)
        for (value in 0 until usersMarker.size) {
            val marker = usersMarker[value]
            textUnderIcon.text = marker.title
            if (zoom <= 19) {
                val covertImage = ConvertToBitmap.Base()
                marker.setIcon(
                    covertImage.convert(
                        applicationContext,
                        R.drawable.marker_other_user
                    )
                )
            } else {
                marker.setIcon(
                    BitmapDescriptorFactory.fromBitmap(ConvertToBitmap.Base().convert(container)))
            }
        }
    }
}