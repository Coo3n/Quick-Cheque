package com.example.quick_cheque.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthenticationResponseDto(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("token")
    val token: String? = null
)