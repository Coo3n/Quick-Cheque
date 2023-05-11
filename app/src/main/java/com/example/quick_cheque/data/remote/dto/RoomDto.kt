package com.example.quick_cheque.data.remote.dto

import com.example.quick_cheque.domain.model.User
import com.google.gson.annotations.SerializedName

data class RoomDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("ownerId")
    val ownerId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("cntCheques")
    val cntCheques: Int,
    @SerializedName("membersRoom")
    var membersRoom: MutableList<User>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String,
)
