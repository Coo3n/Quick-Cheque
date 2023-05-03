package com.example.quick_cheque.data.mapper

import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.domain.model.Room
import com.example.quick_cheque.domain.model.RoomListItem

fun RoomEntity.toRoom(): Room {
    return Room(
        id = id!!,
        title = titleRoom,
        host = ownerId.toString(),
        membersRoom = mutableListOf(),
        cheques = mutableListOf()
    )
}

fun Room.toRoomEntity(): RoomEntity {
    return RoomEntity(
        id = id,
        titleRoom = title,
        ownerId = host.toInt()
    )
}

fun Room.toRoomListItem(): RoomListItem {
    return RoomListItem(
        room = this
    )
}