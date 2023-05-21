package com.example.quick_cheque.domain.repository

import com.example.quick_cheque.data.remote.dto.InsertedChoiceItem
import com.example.quick_cheque.domain.model.Room
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun getRooms(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Room>>>

    suspend fun getRoomById(id: Int): Flow<Resource<Room?>>

    suspend fun insertRoom(room: InsertedChoiceItem): Int

    suspend fun deleteRoom(room: Room)
}