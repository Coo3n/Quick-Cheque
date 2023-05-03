package com.example.quick_cheque.data.local.dao

import androidx.room.*
import com.example.quick_cheque.data.local.entity.ProductEntity
import com.example.quick_cheque.domain.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE id = :id")
    fun getProductById(id: Int): ProductEntity?

    @Insert(entity = ProductEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductEntity)

    @Delete(entity = ProductEntity::class)
    fun deleteProduct(product: ProductEntity)
}