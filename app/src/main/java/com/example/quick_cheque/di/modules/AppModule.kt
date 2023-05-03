package com.example.quick_cheque.di.modules

import android.content.Context
import com.example.quick_cheque.MyApp
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: MyApp) {
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }
}

