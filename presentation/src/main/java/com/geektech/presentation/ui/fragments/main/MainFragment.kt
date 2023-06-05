package com.geektech.presentation.ui.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.geektech.domain.modles.LocModel
import com.geektech.presentation.R
import com.geektech.presentation.ui.fragments.main.map.MapLocUpdates
import com.geektech.presentation.ui.fragments.main.map.MarkerAllUser
import com.geektech.presentation.ui.fragments.main.map.MarkerThisUser
import com.geektech.presentation.ui.fragments.main.map.MarkerTapListener
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var markerAllUser: MarkerAllUser
    private lateinit var locRequest: LocationRequest
    private lateinit var locCallback: LocationCallback
    private lateinit var fusedLocClient: FusedLocationProviderClient
    private lateinit var locUpdates: MapLocUpdates
    private var markerThisUser = MarkerThisUser()
    private val viewModel: MainViewModel by viewModels()
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //1
        uiScope.launch(Dispatchers.IO) {
            locThisUser()
            initialization()
        }
    }

    private fun initialization() {

        fusedLocClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 500)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(2000)
            .setMaxUpdateDelayMillis(500)
            .build()

        //locThisUser()

        markerAllUser = MarkerAllUser(requireContext(), requireActivity().applicationContext)
        locUpdates = MapLocUpdates(fusedLocClient, locRequest, locCallback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        MarkerTapListener(googleMap).invoke()
        mMap.addMarker(MarkerOptions().position(LatLng(42.812348, 73.845534)))
        //2
        viewModel.fetchUsers().observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                markerAllUser.display(it, mMap)
            }
        }

        mMap.setOnCameraMoveStartedListener {
            uiScope.launch {
                val zoom = mMap.cameraPosition.zoom
                markerAllUser.zoomChanged(zoom)
            }
        }
    }

    private fun locThisUser() {
        locCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                val location = locationResult.lastLocation!!
                Log.e("TAGGER", "onLocationResult: $location")
                if (mMap != null) {
                    markerThisUser(location, mMap, requireActivity().applicationContext)
                    viewModel.updateLoc(LocModel(null, location.latitude, location.longitude))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        locUpdates.start(requireContext(), requireActivity())
    }

    override fun onStop() {
        super.onStop()

        locUpdates.stop()
        Toast.makeText(requireContext(), "Well, it's stop ", Toast.LENGTH_SHORT).show()
    }
}