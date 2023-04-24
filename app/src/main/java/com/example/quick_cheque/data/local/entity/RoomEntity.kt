package com.example.quick_cheque.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quick_cheque.domain.model.Cheque
import com.example.quick_cheque.domain.model.User

@Entity
data class RoomEntity(
    val title: String,
    val host: String,
    var membersRoom: MutableList<User>,
    val cheques: MutableList<Cheque>,
    @PrimaryKey val id: Int? = null
)
