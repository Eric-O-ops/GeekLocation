package com.geeks.presentation.ui.fragments.main

import android.content.Context
import android.location.Location
import com.geeks.presentation.models.LocModelUI
import com.geeks.presentation.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class DisplayThisUserOnMap {

    private var marker: Marker? = null

    fun display(location: Location, mMap: GoogleMap, context: Context) {
        val position = LatLng(location.latitude, location.longitude)

        fun createMarker() {
            marker = mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .icon(ConvertToBitmap.Base().convert(context, R.drawable.mark_of_this_user))
                    .rotation(location.bearing)
                    .anchor(0.5f, 0.5f))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 18f))
        }

        fun updatePosition() {
            // todo is null all time ?
            marker?.let {
                it.position = position
                it.rotation = location.bearing
            }
            // marker!!.apply { this.position = position; this.rotation = rotation }
        }

        if (marker == null) createMarker() else updatePosition()
    }

    fun display(locModel: LocModelUI, mMap: GoogleMap, context: Context) {
        val position = LatLng(locModel.latitude, locModel.longitude)

        fun createMarker() {
            marker = mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .icon(ConvertToBitmap.Base().convert(context, R.drawable.mark_of_this_user))
                    .rotation(locModel.bearing!!)
                    .anchor(0.5f, 0.5f))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 18f))
        }

        fun updatePosition() {
            // todo is null all time ?
            marker?.let {
                it.position = position
                it.rotation = locModel.bearing!!
            }
            // marker!!.apply { this.position = position; this.rotation = rotation }
        }

        if (marker == null) createMarker() else updatePosition()
    }
}