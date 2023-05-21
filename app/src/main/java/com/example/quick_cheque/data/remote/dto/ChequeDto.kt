package com.example.quick_cheque.data.remote.dto

import com.example.quick_cheque.domain.model.User
import com.google.gson.annotations.SerializedName

data class ChequeDto(
    @SerializedName("msg")
    val message: List<ChequeData>?
)

data class ChequeData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("admin")
    val isAdmin: Boolean,
    @SerializedName("owner")
    val owner: UserDto,
    @SerializedName("name")
    val title: String,
    @SerializedName("sum")
    val sumCheque: Int,
    @SerializedName("member")
    var membersCheque: MutableList<UserDto>,
)