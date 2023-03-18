package com.example.quick_cheque.model

data class Cheque(
    var title: String,
    var owner: User,
    var sumOfCheque: Int = 0,
    var membersCheque: MutableList<User> = mutableListOf(),
)
