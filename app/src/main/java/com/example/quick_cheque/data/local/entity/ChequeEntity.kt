package com.example.quick_cheque.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cheque")
data class ChequeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val roomId: Int,
    val ownerId: Int,
    val titleCheque: String
)
