package com.example.quick_cheque.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quick_cheque.domain.model.User
import java.math.BigDecimal

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    val id: Int? = null,
    var titleProduct: String,
    var price: Double,
    var count: Int,
)
