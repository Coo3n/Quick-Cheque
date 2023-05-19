package com.example.quick_cheque.data.repository

import android.content.SharedPreferences
import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.mapper.toRoom
import com.example.quick_cheque.data.mapper.toRoomEntity
import com.example.quick_cheque.data.remote.QuickChequeApi
import com.example.quick_cheque.data.remote.dto.InsertedRoomDto
import com.example.quick_cheque.domain.model.ChoiceItem
import com.example.quick_cheque.domain.model.Room
import com.example.quick_cheque.domain.repository.ChoiceItemRepository
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val roomDao: RoomDao,
    private val quickChequeApi: QuickChequeApi,
) : ChoiceItemRepository {
    override suspend fun getChoiceItems(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<ChoiceItem>>> {
        return flow {
            emit(Resource.Loading(true))
            val localRooms = roomDao.getRooms()
            emit(Resource.Success(
                data = localRooms.map { it.toRoom() }
            ))

            val shouldJustLoadOnCache = localRooms.isNotEmpty() && !fetchFromRemote
            if (shouldJustLoadOnCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteResponse = quickChequeApi.getRooms()

            if (remoteResponse.isSuccessful) {
                emit(Resource.Success(
                    data = remoteResponse.body()?.message?.map { it.toRoom() }
                ))

                roomDao.clearRooms()
                roomDao.insertRoomList(remoteResponse.body()!!.message!!.map { it.toRoomEntity() })
            } else {
                emit(Resource.Error(message = "Что-то пошло не так, бро"))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getRoomById(id: Int): Flow<Resource<Room?>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertRoom(room: Room) {
        TODO("Not yet implemented")
    }

    suspend fun insertRoom(room: InsertedRoomDto): Int {
        var insertedRoomId = -1

        try {
            val response = quickChequeApi.addRoom(room)

            if (response.isSuccessful) {
                val ownerId = sharedPreferences.getInt("MY_ID", -1)
                insertedRoomId = response.body()?.id!!
                if (ownerId != -1) {
                    val roomEntity = RoomEntity(
                        id = insertedRoomId,
                        titleRoom = room.title,
                        ownerId = ownerId
                    )
                    roomDao.insertRoom(roomEntity)
                    return insertedRoomId
                }
            }
        } catch (exception: Exception) {
            return insertedRoomId
        }

        return insertedRoomId
    }

    override suspend fun deleteRoom(room: Room) {
        TODO("Not yet implemented")
    }
}