package com.example.quick_cheque.data.remote.dto

import com.example.quick_cheque.domain.model.User
import com.google.gson.annotations.SerializedName

data class RoomDto(
    @SerializedName("msg")
    val message: List<RoomData>,
)

data class RoomData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("admin")
    val isAdmin: Boolean,
    @SerializedName("owner_id")
    val ownerId: Long,
    @SerializedName("name")
    val title: String,
    @SerializedName("cheque_cnt")
    val cntCheques: Int,
    @SerializedName("member")
    var membersRoom: MutableList<User>,
)