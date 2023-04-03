package com.example.quick_cheque

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.quick_cheque.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var btn_locale: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val bottomNav = binding.mainBottomNav
        val navHost = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
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



        setContentView(binding.root)

        var btn_locale_en: Button = findViewById(R.id.radio_english)
        var btn_locale_ru: Button = findViewById(R.id.radio_russian)

        btn_locale_en.setOnClickListener {
            val locale = Locale("en") // выбираем язык
            Locale.setDefault(locale) // устанавливаем его как дефолтный
            val config = resources.configuration // получаем конфигурацию ресурсов
            config.setLocale(locale) // устанавливаем локализацию в конфиг
            resources.updateConfiguration(
                config,
                resources.displayMetrics
            ) // обновляем конфигурацию ресурсов
            recreate() // обновляем активити
        }

        btn_locale_ru.setOnClickListener {
            val locale = Locale("en") // выбираем язык
            Locale.setDefault(locale) // устанавливаем его как дефолтный
            val config = resources.configuration // получаем конфигурацию ресурсов
            config.setLocale(locale) // устанавливаем локализацию в конфиг
            resources.updateConfiguration(
                config,
                resources.displayMetrics
            ) // обновляем конфигурацию ресурсов
            recreate() // обновляем активити
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }
}