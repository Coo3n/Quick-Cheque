package com.example.quick_cheque

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.example.quick_cheque.di.AppComponent
import com.example.quick_cheque.di.DaggerAppComponent
import com.example.quick_cheque.di.modules.AppModule
import java.util.*

class MyApp : Application() {
    lateinit var appComponent: AppComponent
    private lateinit var settings: SharedPreferences

    private fun getSavedTheme() = settings.getInt("theme", -1)
    private fun getSavedLocale() = settings.getString("locale", "")
    private fun setSavedSettings(){
        settings = getSharedPreferences("ui_settings", Context.MODE_PRIVATE)

        val locale = Locale(getSavedLocale())
        Locale.setDefault(locale)
        resources.configuration.setLocale(locale)
        resources.updateConfiguration(
            resources.configuration,
            resources.displayMetrics
        )

        AppCompatDelegate.setDefaultNightMode(getSavedTheme())
    }
    override fun onCreate() {
        super.onCreate()
        setSavedSettings()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}