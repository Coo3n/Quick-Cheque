package com.example.quick_cheque.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val titleRoom: String,
    val ownerId: Long,
)
