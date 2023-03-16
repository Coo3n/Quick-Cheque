package com.example.quick_cheque.model

data class Cheque(
    var title: String,
    var owner: User,
    var sumOfCheque: Int,
)
