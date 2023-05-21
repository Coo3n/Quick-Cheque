package com.example.quick_cheque.data.repository

import android.content.SharedPreferences
import com.example.quick_cheque.data.local.dao.ProductDao
import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.data.remote.QuickChequeApi
import com.example.quick_cheque.domain.model.Product
import com.example.quick_cheque.domain.repository.ProductRepository
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val productDao: ProductDao,
    private val quickChequeApi: QuickChequeApi,
) : ProductRepository {
    override suspend fun getProducts(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Product>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(id: Int): Flow<Resource<Product?>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertProduct(product: Product): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(product: Product) {
        TODO("Not yet implemented")
    }
}