package com.example.quick_cheque.presentation.screen.main_pages_fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentProfileScreenBinding
import com.example.quick_cheque.presentation.screen.BaseFragment

class ProfileScreenFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileScreenBinding
    private lateinit var settings: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settings = (requireActivity().application as MyApp)
            .getSharedPreferences("ui_settings", Context.MODE_PRIVATE)
        binding = FragmentProfileScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.fragmentProfileOptSettings.setOnClickListener {
//            findNavController().navigate(R.id.action_profileScreenFragment_to_fragmentSettings)
//        }

        val switchTheme = binding.switchThemeBtn
        val switchLang = binding.switchLanguageBtn

        when (getSavedTheme()) {
            AppCompatDelegate.MODE_NIGHT_YES -> switchTheme.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> switchTheme.isChecked = false
        }

        if (getSavedLocale()?.contains("ru") == true) {
            switchLang.isChecked = false
        } else if (getSavedLocale()?.contains("en") == true) {
            switchLang.isChecked = true
        }

        switchTheme.setOnClickListener {
            if (getSavedTheme() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveTheme(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveTheme(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        switchLang.setOnClickListener {
            if (switchLang.isChecked) {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("en-EN")
                saveLocale("en-EN")
                AppCompatDelegate.setApplicationLocales(appLocale)

            }
            else {
                val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags("ru-RU")
                saveLocale("ru-RU")
                AppCompatDelegate.setApplicationLocales(appLocale)
            }
        }
    }

    private fun saveTheme(theme: Int) = settings.edit().putInt("theme", theme).apply()
    private fun saveLocale(locale: String) = settings.edit().putString("locale", locale).apply()
    private fun getSavedTheme() = settings.getInt("theme", -1)
    private fun getSavedLocale() = settings.getString("locale", "")
}