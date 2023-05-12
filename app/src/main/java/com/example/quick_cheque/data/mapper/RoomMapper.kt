package com.example.quick_cheque.data.mapper

import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.remote.dto.RoomData
import com.example.quick_cheque.data.remote.dto.RoomDto
import com.example.quick_cheque.domain.model.Room
import com.example.quick_cheque.domain.model.RoomListItem
import com.example.quick_cheque.domain.model.User

// dto - room
// dto - roomEntity

//entity - room
// room - RoomListItem

fun RoomEntity.toRoom(): Room {
    return Room(
        id = id!!,
        title = titleRoom,
        ownerId = ownerId,
        membersRoom = mutableListOf(), // ?
        cntCheques = 0,                // ?
    )
}

fun RoomData.toRoom(): Room {
    return Room(
        id = id,
        title = title,
        ownerId = ownerId,
        membersRoom = membersRoom,
        cntCheques = cntCheques,
    )
}

fun RoomData.toRoomEntity(): RoomEntity {
    return RoomEntity(
        id = id,
        titleRoom = title,
        ownerId = ownerId
    )
}

fun Room.toRoomListItem(): RoomListItem {
    return RoomListItem(
        room = this
    )
}