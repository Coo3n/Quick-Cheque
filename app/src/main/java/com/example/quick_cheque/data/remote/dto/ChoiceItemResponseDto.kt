package com.example.quick_cheque.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ChoiceItemResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("msg")
    val msg: String? = null,
)