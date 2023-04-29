package com.example.quick_cheque.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RoomEntityWithCheques(
    @Embedded val room: RoomEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "roomId"
    )
    val cheques: List<ChequeEntity>
)
