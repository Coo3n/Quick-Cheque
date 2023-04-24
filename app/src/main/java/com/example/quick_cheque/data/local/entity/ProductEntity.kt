package com.example.quick_cheque.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quick_cheque.domain.model.User
import java.math.BigDecimal

@Entity
data class ProductEntity(
    var titleProduct: String,
    var price: BigDecimal,
    var count: Int,
    var membersProduct: MutableList<User> = mutableListOf(),
    @PrimaryKey
    val id: Int? = null
)
