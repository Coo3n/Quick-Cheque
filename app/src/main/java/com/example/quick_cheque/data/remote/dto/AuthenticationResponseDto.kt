package com.example.quick_cheque.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthenticationResponseDto(
    @SerializedName("msg")
    val message: String,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("token")
    val token: String? = null
)