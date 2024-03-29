package com.example.quick_cheque.presentation.screen.main_pages_fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.databinding.FragmentSettingsBinding
import com.example.quick_cheque.presentation.screen.BaseFragment
import java.util.*

class FragmentSettings : BaseFragment() {
    private lateinit var settings: SharedPreferences

    private var _binding: FragmentSettingsBinding? = null
    val binding: FragmentSettingsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settings = (requireActivity().application as MyApp)
            .getSharedPreferences("ui_settings", Context.MODE_PRIVATE)
        _binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val config = resources.configuration
        val radioRu = binding.languageRadiogroupRu
        val radioEn = binding.languageRadiogroupEn
        val themeDay = binding.themeRadiogroupDay
        val themeNight = binding.themeRadiogroupNight

        if (getSavedLocale()?.contains("ru") == true) {
            radioRu.isChecked = true
        } else if (getSavedLocale()?.contains("en") == true) {
            radioEn.isChecked = true
        }

        Log.d("MyTag", getSavedTheme().toString())

        when (getSavedTheme()) {
            AppCompatDelegate.MODE_NIGHT_YES -> themeNight.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> themeDay.isChecked = true
        }

        radioEn.setOnClickListener {
            val locale = Locale("en")
            Locale.setDefault(locale)
            config.setLocale(locale)
            resources.updateConfiguration(
                config,
                resources.displayMetrics
            )
            saveLocale("en")
        }

        radioRu.setOnClickListener {
            val locale = Locale("ru")
            Locale.setDefault(locale)
            config.setLocale(locale)
            resources.updateConfiguration(
                config,
                resources.displayMetrics
            )
            saveLocale("en")

        }

        themeDay.setOnClickListener {
            //if (getSavedTheme() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveTheme(AppCompatDelegate.MODE_NIGHT_NO)
                Log.d("MyTag", getSavedTheme().toString() + "1")
            //}
        }

        themeNight.setOnClickListener {
            //if (getSavedTheme() == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveTheme(AppCompatDelegate.MODE_NIGHT_YES)
                Log.d("MyTag", getSavedTheme().toString() + "2")

            //}
        }
    }

    private fun saveTheme(theme: Int) = settings.edit().putInt("theme", theme).apply()
    private fun saveLocale(locale: String) = settings.edit().putString("locale", locale).apply()
    private fun getSavedTheme() = settings.getInt("theme", -1)
    private fun getSavedLocale() = settings.getString("locale", "")
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}