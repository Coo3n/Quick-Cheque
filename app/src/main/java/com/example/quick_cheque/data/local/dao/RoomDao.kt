package com.example.quick_cheque.data.local.dao

import androidx.room.*
import com.example.quick_cheque.domain.model.Room
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Query("SELECT * FROM room")
    suspend fun getRooms(): Flow<List<Room>>?

    @Query("SELECT * FROM room WHERE id = :id")
    suspend fun getRoomById(id: Int): Room?

    @Insert(entity = Room::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: Room)

    @Delete(entity = Room::class)
    suspend fun deleteRoom(room: Room)
}