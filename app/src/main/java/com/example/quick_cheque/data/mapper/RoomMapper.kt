package com.example.quick_cheque.data.mapper

import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.local.entity.RoomWithUser
import com.example.quick_cheque.data.remote.dto.RoomData
import com.example.quick_cheque.domain.model.Room
import com.example.quick_cheque.domain.model.RoomListItem


fun RoomWithUser.toRoom(): Room {
    return Room(
        id = room.id,
        isAdmin = room.isAdmin,
        title = room.title,
        owner = owner.toUser(),
        membersRoom = members.map { it.toUser() }.toMutableList(),
        cntCheques = room.cntCheques,
    )
}

fun RoomData.toRoom(): Room {
    return Room(
        id = id,
        isAdmin = isAdmin,
        title = title,
        owner = owner.toUser(),
        membersRoom = membersRoom.map { it.toUser() }.toMutableList(),
        cntCheques = cntCheques,
    )
}

fun RoomData.toRoomEntity(): RoomEntity {
    return RoomEntity(
        id = id,
        isAdmin = isAdmin,
        ownerId = owner.id,
        title = title,
        cntCheques = cntCheques,
        members = membersRoom.map { it.id }
    )
}

fun Room.toRoomListItem(): RoomListItem {
    return RoomListItem(
        room = this
    )
}