package com.geektech.presentation.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.geektech.presentation.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), StartDestination {

    @Inject
    lateinit var auth: FirebaseAuth
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        setDestId()
    }

    override fun setDestId() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val currentUser = auth.currentUser

        if (currentUser != null) {
           /* StartDestination
                .Base(navHostFragment,R.navigation.nav_graph, R.id.mainFragment)
                .setDestId()*/
            registerPermissionListener(navHostFragment)
            checkLocationPermission(navHostFragment)
        } else {
            StartDestination
                .Base(navHostFragment,R.navigation.nav_graph, R.id.signInFragment)
                .setDestId()
        }
    }

    private fun registerPermissionListener(navHostFragment: NavHostFragment) {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && it[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                // permission granted
                StartDestination
                    .Base(navHostFragment,R.navigation.nav_graph, R.id.mainFragment)
                    .setDestId()
            } else {
                // permission denied
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkLocationPermission(navHostFragment: NavHostFragment) {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
               this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                StartDestination
                    .Base(navHostFragment,R.navigation.nav_graph, R.id.mainFragment)
                    .setDestId()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                //go to settings "we need your permission"
                Toast.makeText(this, "Go to Settings", Toast.LENGTH_SHORT).show()
            }

            else -> {
                pLauncher.launch( arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ))
            }
        }
    }
}