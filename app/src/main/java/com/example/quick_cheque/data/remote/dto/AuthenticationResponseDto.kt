package com.example.quick_cheque.data.remote.dto

data class AuthenticationResponseDto(
    val message: String,
    val status: String,
    val id: Int? = null,
    val token: String? = null
)
