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
import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.databinding.ActivityMainBinding
import com.example.quick_cheque.di.AppComponent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavController: BottomNavigationView

    @Inject lateinit var roomDao: RoomDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as MyApp).appComponent.injectMainActivity(this)

        val nav = supportFragmentManager.findFragmentById(
            R.id.fragment_container_view
        ) as NavHost
        navController = nav.navController

        bottomNavController = binding.mainBottomNav

        setupActionBarWithNavController(navController)
        supportActionBar?.hide()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setupBottomNavController()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupBottomNavController() {
        bottomNavController.setupWithNavController(navController)
        bottomNavController.setOnItemReselectedListener { }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.joinScreenFragment -> {
                    bottomNavController.visibility = View.VISIBLE
                }
                R.id.profileScreenFragment -> {
                    bottomNavController.visibility = View.VISIBLE
                }
                R.id.choiceRoomFragment -> {
                    bottomNavController.visibility = View.VISIBLE
                }
                R.id.createScreenFragment -> {
                    bottomNavController.visibility = View.VISIBLE
                }
                R.id.fragmentSettings -> {
                    bottomNavController.visibility = View.VISIBLE
                }
                else -> bottomNavController.visibility = View.GONE
            }
        }
    }
}