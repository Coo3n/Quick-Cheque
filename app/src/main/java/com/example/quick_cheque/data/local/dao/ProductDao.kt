package com.example.quick_cheque.data.local.dao

import androidx.room.*
import com.example.quick_cheque.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getProducts(): Flow<List<Product>>?

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id: Int): Product?

    @Insert(entity = Product::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Delete(entity = Product::class)
    suspend fun deleteProduct(product: Product)
}