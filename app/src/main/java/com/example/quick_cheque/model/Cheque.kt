package com.example.quick_cheque.model

import java.math.BigDecimal

data class Cheque(
    var title: String,
    var owner: User,
    var sumOfCheque: BigDecimal = BigDecimal(0),
    var membersCheque: MutableList<User> = mutableListOf(),
)
