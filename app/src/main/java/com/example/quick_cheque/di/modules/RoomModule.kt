package com.example.quick_cheque.di.modules

import android.content.Context
import androidx.room.Room
import com.example.quick_cheque.data.local.QuickChequeDataBase
import com.example.quick_cheque.data.local.dao.ChequeDao
import com.example.quick_cheque.data.local.dao.ProductDao
import com.example.quick_cheque.data.local.dao.RoomDao
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
    fun provideQuickChequeDataBase(context: Context): QuickChequeDataBase {
        return Room.databaseBuilder(
            context,
            QuickChequeDataBase::class.java,
            "QuickChequeDataBase"
        ).allowMainThreadQueries().build()
    }
}
