package com.example.quick_cheque.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.quick_cheque.data.local.conventor.IntegerListConverter
import com.example.quick_cheque.data.local.dao.ChequeDao
import com.example.quick_cheque.data.local.dao.ProductDao
import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.data.local.entity.*

@Database(
    entities = [ChequeEntity::class, ProductEntity::class, RoomEntity::class, UserEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(IntegerListConverter::class)
abstract class QuickChequeDataBase : RoomDatabase() {
    abstract fun roomDao(): RoomDao
    abstract fun chequeDao(): ChequeDao
    abstract fun productDao(): ProductDao

    companion object {
        private var quickChequeDataBaseInstance: QuickChequeDataBase? = null
        fun getQuickChequeDataBaseInstance(context: Context): QuickChequeDataBase {
            return if (quickChequeDataBaseInstance == null) {
                Room.databaseBuilder(
                    context,
                    QuickChequeDataBase::class.java,
                    "QuickChequeDataBase"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            } else {
                quickChequeDataBaseInstance!!
            }
        }
    }
}