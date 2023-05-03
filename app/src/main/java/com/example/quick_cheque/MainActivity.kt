package com.example.quick_cheque

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.quick_cheque.databinding.ActivityMainBinding
import com.example.quick_cheque.di.AppComponent
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavController: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as MyApp).appComponent.injectMainActivity(this)

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
                R.id.joinScreenFragment -> {
                    navController.popBackStack(R.id.joinScreenFragment, false)
                    bottomNavController.visibility = View.VISIBLE
                }
                R.id.profileScreenFragment -> {
                    navController.popBackStack(R.id.profileScreenFragment, false)
                    bottomNavController.visibility = View.VISIBLE
                }
                R.id.choiceRoomFragment -> {
                    navController.popBackStack(R.id.choiceRoomFragment, false)
                    bottomNavController.visibility = View.VISIBLE
                }
                R.id.createScreenFragment -> {
                    navController.popBackStack(R.id.createScreenFragment, false)
                    bottomNavController.visibility = View.VISIBLE
                }
                R.id.fragmentSettings -> {
                    navController.popBackStack(R.id.fragmentSettings, false)
                    bottomNavController.visibility = View.VISIBLE
                }
                else -> bottomNavController.visibility = View.GONE
            }
        }
    }
}