package com.example.quick_cheque.di.module

import android.content.Context
import android.content.SharedPreferences
import com.example.quick_cheque.data.local.QuickChequeDataBase
import com.example.quick_cheque.data.local.dao.ChequeDao
import com.example.quick_cheque.data.local.dao.ProductDao
import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.data.remote.QuickChequeApi
import com.example.quick_cheque.data.repository.AuthenticationRepositoryImpl
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    @Provides
    fun provideRoomDao(quickChequeDataBase: QuickChequeDataBase): RoomDao {
        return quickChequeDataBase.roomDao()
    }

    @Provides
    fun provideProductDao(quickChequeDataBase: QuickChequeDataBase): ProductDao {
        return quickChequeDataBase.productDao()
    }

    @Provides
    fun provideChequeDao(quickChequeDataBase: QuickChequeDataBase): ChequeDao {
        return quickChequeDataBase.chequeDao()
    }

    @Provides
    fun provideAuthenticationRepository(
        sharedPreferences: SharedPreferences,
        quickChequeApi: QuickChequeApi
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(sharedPreferences, quickChequeApi)
    }

    @Provides
    fun provideRoomRepositoryImpl(
        sharedPreferences: SharedPreferences,
        roomDao: RoomDao,
        quickChequeApi: QuickChequeApi
    ): RoomRepositoryImpl {
        return RoomRepositoryImpl(sharedPreferences, roomDao, quickChequeApi)
    }

    @Provides
    fun provideQuickChequeDataBase(context: Context): QuickChequeDataBase {
        return QuickChequeDataBase.getQuickChequeDataBaseInstance(context)
    }
}
