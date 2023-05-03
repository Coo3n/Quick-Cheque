package com.example.quick_cheque.domain.repository

import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.local.entity.RoomEntityWithCheques

interface RoomRepository {
    fun getRooms(): List<RoomEntity>

    fun getRoomById(id: Int): RoomEntity?

    fun getRoomWithCheques(id: Int): List<RoomEntityWithCheques>

    fun insertRoom(room: RoomEntity)

    fun deleteRoom(room: RoomEntity)
}