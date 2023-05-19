package com.example.quick_cheque.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InsertedRoomDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("msg")
    val msg: String? = null,
    @SerializedName("name")
    val title: String
)
