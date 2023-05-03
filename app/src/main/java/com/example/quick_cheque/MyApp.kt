package com.example.quick_cheque

import android.app.Application
import com.example.quick_cheque.di.AppComponent
import com.example.quick_cheque.di.DaggerAppComponent
import com.example.quick_cheque.di.modules.AppModule

class MyApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
    }
}