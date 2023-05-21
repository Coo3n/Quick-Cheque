package com.example.quick_cheque.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RoomWithUser(
    @Embedded val room: RoomEntity,
    @Relation(
        parentColumn = "ownerId",
        entityColumn = "id"
    )

    val owner: UserEntity,
    @Relation(
        parentColumn = "members",
        entityColumn = "id"
    )

    val members: List<UserEntity>
)
