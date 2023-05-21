package com.example.quick_cheque.data.local.dao

import androidx.room.*
import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.local.entity.RoomWithUser
import com.example.quick_cheque.data.local.entity.UserEntity

@Dao
interface RoomDao {
    @Transaction
    @Query("SELECT * FROM rooms")
    fun getRooms(): List<RoomWithUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom(room: RoomEntity) // вставляем комнату

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoomList(roomList: List<RoomEntity>) // вставляем комнаты

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userEntity: UserEntity) // добавляем юзера

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListUser(listUserEntity: List<UserEntity>) // добавляем список юзеров

    @Delete(entity = RoomEntity::class)
    fun deleteRoom(room: RoomEntity)

    @Query("DELETE FROM rooms")
    fun clearRooms()
}