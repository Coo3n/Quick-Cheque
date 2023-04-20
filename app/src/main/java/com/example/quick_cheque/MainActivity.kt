package com.example.quick_cheque

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.quick_cheque.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
    private lateinit var bottomNavController: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavController = binding.mainBottomNav

        val nav = supportFragmentManager.findFragmentById(
            R.id.fragment_container_view
        ) as NavHost

        navController = nav.navController

        setupActionBarWithNavController(navController)
        supportActionBar?.hide()

        setupBottomNavController()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupBottomNavController() {
        bottomNavController.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.joinScreenFragment -> bottomNavController.visibility = View.VISIBLE
                R.id.profileScreenFragment -> bottomNavController.visibility = View.VISIBLE
                R.id.mainScreenFragment -> bottomNavController.visibility = View.VISIBLE
                R.id.fragmentSettings -> bottomNavController.visibility = View.VISIBLE
                else -> bottomNavController.visibility = View.GONE
            }
        }
    }
}