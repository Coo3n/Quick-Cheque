package com.example.quick_cheque.data.remote.dto

import com.google.gson.annotations.SerializedName


data class UserDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)
