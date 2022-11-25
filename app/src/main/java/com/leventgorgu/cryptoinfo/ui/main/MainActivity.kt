package com.leventgorgu.cryptoinfo.ui.main

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.leventgorgu.cryptoinfo.R
import com.leventgorgu.cryptoinfo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        findViewById<BottomNavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> showBottomNav()
                R.id.navigation_favorites -> showBottomNav()
                else -> hideBottomNav()
            }
        }

        /*
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favorites, //R.id.navigation_notifications
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        */
        navView.setupWithNavController(navController)
    }
    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE

    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }
}