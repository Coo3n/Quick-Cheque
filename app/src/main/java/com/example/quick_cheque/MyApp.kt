package com.example.quick_cheque

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.core.os.LocaleListCompat
import com.example.quick_cheque.di.AppComponent
import com.example.quick_cheque.di.DaggerAppComponent
import com.example.quick_cheque.di.modules.AppModule
import java.util.*

class MyApp : Application() {
    lateinit var appComponent: AppComponent
    private lateinit var settings: SharedPreferences


    override fun onCreate() {
        super.onCreate()
        setSavedSettings()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }

    private fun getSavedTheme() = settings.getInt("theme", -1)
    private fun getSavedLocale() = settings.getString("locale", "ru")
    private fun setSavedSettings() {
        settings = getSharedPreferences("ui_settings", Context.MODE_PRIVATE)

        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(getSavedLocale())
        AppCompatDelegate.setApplicationLocales(appLocale)

        AppCompatDelegate.setDefaultNightMode(getSavedTheme())
    }
}