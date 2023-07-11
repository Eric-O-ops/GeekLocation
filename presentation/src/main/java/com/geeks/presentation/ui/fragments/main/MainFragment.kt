package com.geeks.presentation.ui.fragments.main

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent.DispatcherState
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.geeks.presentation.R
import com.geeks.presentation.base.extensions.checkSinglePermission
import com.geeks.presentation.base.extensions.listToListUI
import com.geeks.presentation.base.extensions.showAlertDialog
import com.geeks.presentation.models.toUI
import com.geeks.presentation.ui.fragments.main.map.allusers.MarkerAllUser
import com.geeks.presentation.ui.fragments.main.map.allusers.MarkerTapListener
import com.geeks.presentation.ui.fragments.main.map.thisuser.DisplayThisUserOnMap
import com.geeks.presentation.ui.fragments.signin.toast
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var bLauncher: ActivityResultLauncher<String>
    private lateinit var markerAllUser: MarkerAllUser
    private val thisUser = DisplayThisUserOnMap()
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permission ->
            if (permission[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permission[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                checkAccessBackgroundLocation()
                checkInternetAndGPS()
                initialization()

            } else {
                toast(R.string.permissions_denied)
            }
        }

        when {
            checkSinglePermission(Manifest.permission.ACCESS_COARSE_LOCATION) &&
                    checkSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                checkAccessBackgroundLocation()
                checkInternetAndGPS()
                initialization()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                toast(R.string.settings)
            }

            else -> {
                pLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    )
                )
            }
        }

        bLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) toast("background granted") else toast("background not granted")
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkAccessBackgroundLocation() {
        if (checkSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) return
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.massage_need_permission_for_background)
            .setPositiveButton(R.string.ok) { _, _ ->
                bLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }.setNegativeButton(R.string.negative_button_for_background) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    private fun initialization() {
        viewModel.startLocationRequest {
            Log.e("THISUSERLOC", it.toString())
            thisUser.display(it.toUI(), mMap, requireContext())
            viewModel.updateLoc(it)

        }
    }

    private fun checkInternetAndGPS() {
        when {
            !viewModel.isGpsEnabled() && !viewModel.isNetworkEnable() -> {
                showAlertDialog(
                    R.string.title_no_network_and_GPS,
                    R.string.massage_no_network_and_GPS
                )
            }
            !viewModel.isGpsEnabled() -> {
                showAlertDialog(R.string.title_no_GPS_enabled, R.string.massage_no_GPS_enabled)
            }
            !viewModel.isNetworkEnable() -> {
                showAlertDialog(R.string.title_no_network, R.string.massage_no_network)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        MarkerTapListener(googleMap).invoke()
        //2
        markerAllUser = MarkerAllUser(requireContext(), requireActivity().applicationContext)

        viewModel.fetchUsers {
            markerAllUser.display(listToListUI(it), mMap)
        }


        mMap.setOnCameraMoveStartedListener {
            val zoom = mMap.cameraPosition.zoom
            markerAllUser.zoomChanged(zoom)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        viewModel.stopLocationRequest()
    }
}