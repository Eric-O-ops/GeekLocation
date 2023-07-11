package com.geeks.presentation.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.NavHostFragment
import com.geeks.presentation.R
import com.geeks.presentation.base.extensions.checkSinglePermission
import com.geeks.presentation.ui.services.LocationService
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), StartDestination {

    @Inject
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        setDestId()
    }

    override fun setDestId() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        //todo replaced to data layer
        if (auth.currentUser != null) {
            StartDestination
                .Base(navHostFragment, R.navigation.nav_graph, R.id.mainFragment)
                .setDestId()
        } else {
            StartDestination
                .Base(navHostFragment, R.navigation.nav_graph, R.id.signInFragment)
                .setDestId()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

       stopLocationService()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStop() {
        super.onStop()
        if (checkSinglePermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
            startForegroundService(Intent(this, LocationService::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        stopLocationService()
    }

    private fun stopLocationService() {
        if (LocationService.SERVICE_RUNNING) {
            stopService(Intent(this, LocationService::class.java))
        }
    }

}
