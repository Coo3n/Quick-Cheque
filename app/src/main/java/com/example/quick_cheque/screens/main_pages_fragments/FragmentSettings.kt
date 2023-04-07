package com.example.quick_cheque.screens.main_pages_fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentSettingsBinding
import com.example.quick_cheque.screens.BaseFragment
import java.util.*

class FragmentSettings : BaseFragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateToolbar(setDisplayHome = false, setDisplaySearch = false)

        binding.languageRadiogroupEn.setOnClickListener {
            val locale = Locale("en") // выбираем язык
            Locale.setDefault(locale) // устанавливаем его как дефолтный
            val config = resources.configuration // получаем конфигурацию ресурсов
            config.setLocale(locale) // устанавливаем локализацию в конфиг
            resources.updateConfiguration(
                config,
                resources.displayMetrics
            ) // обновляем конфигурацию ресурсов
        }

        binding.languageRadiogroupRu.setOnClickListener {
            val locale = Locale("ru") // выбираем язык
            Locale.setDefault(locale) // устанавливаем его как дефолтный
            val config = resources.configuration // получаем конфигурацию ресурсов
            config.setLocale(locale) // устанавливаем локализацию в конфиг
            resources.updateConfiguration(
                config,
                resources.displayMetrics
            ) // обновляем конфигурацию ресурсов
        }

        binding.themeRadiogroupNight.setOnClickListener {
            val currentNightMode =
                resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (currentNightMode) {
                Configuration.UI_MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }
}