package com.example.quick_cheque.domain.repository

import com.example.quick_cheque.domain.model.ChoiceItem
import com.example.quick_cheque.domain.model.Room
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChoiceItemRepository {
    suspend fun getChoiceItems(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<ChoiceItem>>>

    suspend fun getRoomById(id: Int): Flow<Resource<Room?>>

    suspend fun insertRoom(room: Room)

    suspend fun deleteRoom(room: Room)
}