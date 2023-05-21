package com.example.quick_cheque.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey val id: Int,
    val isAdmin: Boolean,
    val ownerId: Int,
    val title: String,
    val cntCheques: Int,
    val members: List<Int>,
)

