package com.example.quick_cheque.data.local.dao

import androidx.room.*
import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.local.entity.RoomEntityWithCheques

@Dao
interface RoomDao {
    @Query("SELECT * FROM room")
    fun getRooms(): List<RoomEntity>

    @Query("SELECT * FROM room WHERE id = :id")
    fun getRoomById(id: Int): RoomEntity?

    @Transaction
    @Query("SELECT* FROM room WHERE id = :id")
    fun getRoomWithCheques(id: Int): List<RoomEntityWithCheques>

    @Insert(entity = RoomEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom(room: RoomEntity)

    @Insert(entity = RoomEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertRoomList(roomList: List<RoomEntity>)

    @Delete(entity = RoomEntity::class)
    fun deleteRoom(room: RoomEntity)

    @Query("DELETE FROM room")
    fun clearRooms()
}