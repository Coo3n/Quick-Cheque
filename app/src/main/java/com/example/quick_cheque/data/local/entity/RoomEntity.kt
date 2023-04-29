package com.example.quick_cheque.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val titleRoom: String,
    val ownerId: Int,
)
