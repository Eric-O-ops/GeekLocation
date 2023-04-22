package com.geektech.presentation.ui.activity

import androidx.navigation.fragment.NavHostFragment

interface StartDestination {

    fun setDestId()

    class Base(
      private val navHostFragment: NavHostFragment,
      private val navGraph: Int,
      private val fragmentId: Int
    ) : StartDestination {

        override fun setDestId() {
            val navController = navHostFragment.navController
            val navGraph = navController.navInflater.inflate(navGraph)

            navGraph.setStartDestination(fragmentId)
            navController.graph = navGraph
        }
    }
}