package com.example.quick_cheque.data.repository

import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.local.entity.RoomEntityWithCheques
import com.example.quick_cheque.domain.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: RoomDao
) : RoomRepository {
    override fun getRooms(): List<RoomEntity> {
        return roomDao.getRooms()
    }

    override fun getRoomById(id: Int): RoomEntity? {
        return roomDao.getRoomById(id)
    }

    override fun getRoomWithCheques(id: Int): List<RoomEntityWithCheques> {
        return roomDao.getRoomWithCheques(id)
    }

    override fun insertRoom(room: RoomEntity) {
        roomDao.insertRoom(room)
    }

    override fun deleteRoom(room: RoomEntity) {
        roomDao.deleteRoom(room)
    }
}