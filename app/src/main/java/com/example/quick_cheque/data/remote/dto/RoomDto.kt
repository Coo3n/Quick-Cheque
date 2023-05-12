package com.example.quick_cheque.data.remote.dto

import com.example.quick_cheque.domain.model.User
import com.google.gson.annotations.SerializedName

data class RoomDto(
    @SerializedName("message")
    val message: List<RoomData>,
    @SerializedName("status")
    val status: String,
)

data class RoomData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("owner_id")
    val ownerId: Long,
    @SerializedName("name")
    val title: String,
    @SerializedName("cheque_cnt")
    val cntCheques: Int,
    @SerializedName("members")
    var membersRoom: MutableList<User>,
)