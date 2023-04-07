package com.example.quick_cheque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.quick_cheque.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.MobileAds

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val bottomNav = binding.mainBottomNav
        val navHost = supportFragmentManager.findFragmentById(
            binding.fragmentContainerView.id
        ) as NavHostFragment
        val navController = navHost.navController

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.joinScreenFragment -> bottomNav.visibility = View.VISIBLE
                R.id.profileScreenFragment -> bottomNav.visibility = View.VISIBLE
                R.id.mainScreenFragment -> bottomNav.visibility = View.VISIBLE
                R.id.fragmentSettings -> bottomNav.visibility = View.VISIBLE
                else -> bottomNav.visibility = View.GONE
            }
        }

        setupToolbar()
        setContentView(binding.root)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.toolbarQuickCheque.root
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_with_search, menu)
        return true
    }
}