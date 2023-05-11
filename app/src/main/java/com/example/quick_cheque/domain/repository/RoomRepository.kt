package com.example.quick_cheque.domain.repository

import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.local.entity.RoomEntityWithCheques
import com.example.quick_cheque.domain.model.Room
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun getMyRooms(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Room>>>

    suspend fun getRoomById(id: Int): Flow<Resource<Room?>>

    suspend fun insertRoom(room: Room)

    suspend fun deleteRoom(room: Room)
}