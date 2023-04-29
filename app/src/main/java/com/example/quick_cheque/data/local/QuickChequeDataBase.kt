package com.example.quick_cheque.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quick_cheque.data.local.dao.ChequeDao
import com.example.quick_cheque.data.local.dao.ProductDao
import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.data.local.entity.ChequeEntity
import com.example.quick_cheque.data.local.entity.ProductEntity
import com.example.quick_cheque.data.local.entity.RoomEntity

@Database(
    entities = [ChequeEntity::class, ProductEntity::class, RoomEntity::class],
    version = 1,
    exportSchema = false
)
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
                ).allowMainThreadQueries().build()
            } else {
                quickChequeDataBaseInstance!!
            }
        }
    }
}