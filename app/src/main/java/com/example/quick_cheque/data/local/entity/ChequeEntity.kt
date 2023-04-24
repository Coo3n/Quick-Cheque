package com.example.quick_cheque.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quick_cheque.domain.model.Product
import com.example.quick_cheque.domain.model.User
import java.math.BigDecimal

@Entity
data class ChequeEntity(
    var title: String,
    var owner: User,
    var sumOfCheque: BigDecimal = BigDecimal(0),
    var products: MutableList<Product> = mutableListOf(),
    var membersCheque: MutableList<User> = mutableListOf(),
    @PrimaryKey val id: Int? = null
)
