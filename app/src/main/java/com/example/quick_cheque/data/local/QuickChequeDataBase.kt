package com.example.quick_cheque.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.quick_cheque.data.local.dao.ChequeDao
import com.example.quick_cheque.data.local.dao.ProductDao
import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.domain.model.Cheque
import com.example.quick_cheque.domain.model.Product
import com.example.quick_cheque.domain.model.Room

@Database(
    entities = [Cheque::class, Product::class, Room::class],
    version = 1
)
abstract class QuickChequeDataBase : RoomDatabase() {
    abstract val roomDao: RoomDao
    abstract val chequeDao: ChequeDao
    abstract val productDao: ProductDao
}