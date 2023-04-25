package com.geektech.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.geektech.presentation.R
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

        val currentUser = auth.currentUser

        if (currentUser != null) {
            StartDestination
                .Base(navHostFragment,R.navigation.nav_graph, R.id.mainFragment)
                .setDestId()
        } else {
            StartDestination
                .Base(navHostFragment,R.navigation.nav_graph, R.id.signInFragment)
                .setDestId()
        }
    }
}