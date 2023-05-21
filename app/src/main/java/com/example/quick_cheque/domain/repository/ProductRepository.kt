package com.example.quick_cheque.domain.repository

import com.example.quick_cheque.domain.model.Product
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Product>>>

    suspend fun getProductById(id: Int): Flow<Resource<Product?>>

    suspend fun insertProduct(product: Product): Int

    suspend fun deleteProduct(product: Product)
}