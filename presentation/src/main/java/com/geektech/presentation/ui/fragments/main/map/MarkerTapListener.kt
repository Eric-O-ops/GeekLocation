package com.geektech.presentation.ui.fragments.main.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap

class MarkerTapListener(
    private val mMap: GoogleMap
) {

   operator fun invoke() {
        mMap.setOnMarkerClickListener { marker ->
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 18f))
            true
        }
    }
}