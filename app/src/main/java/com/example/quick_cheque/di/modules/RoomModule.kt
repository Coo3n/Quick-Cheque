package com.example.quick_cheque.di.modules

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quick_cheque.data.local.QuickChequeDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideRoomDataBase(app: Application): RoomDatabase {
        return Room.databaseBuilder(
            app,
            QuickChequeDataBase::class.java,
            "local-db"
        ).build()
    }

}