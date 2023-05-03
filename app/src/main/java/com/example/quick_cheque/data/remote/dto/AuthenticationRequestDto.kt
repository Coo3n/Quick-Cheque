package com.example.quick_cheque.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AuthenticationRequestDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("password")
    val password: String
)
