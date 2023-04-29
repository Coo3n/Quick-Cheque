package com.example.quick_cheque.di.modules

import android.content.Context
import androidx.room.Room
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.data.local.QuickChequeDataBase
import com.example.quick_cheque.data.local.dao.ChequeDao
import com.example.quick_cheque.data.local.dao.ProductDao
import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.presentation.screen.viewmodels.RegisterViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: MyApp) {
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }
}

