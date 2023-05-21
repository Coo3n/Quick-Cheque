package com.example.quick_cheque.data.remote.dto

import com.google.gson.annotations.SerializedName

data class InsertedChoiceItem(
    @SerializedName("name")
    val title: String,
    @SerializedName("room_id")
    val roomId: Int? = null
)
