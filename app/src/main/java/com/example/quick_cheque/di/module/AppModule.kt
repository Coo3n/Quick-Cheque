package com.example.quick_cheque.di.module

import android.content.Context
import android.content.SharedPreferences
import com.example.quick_cheque.MyApp
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: MyApp) {
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }

    @Provides
    fun provideSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
    }
}

