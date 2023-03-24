package com.example.quick_cheque.model

import java.math.BigDecimal

data class Product(
    var titleProduct: String,
    var price: BigDecimal,
    var count: Int
)

