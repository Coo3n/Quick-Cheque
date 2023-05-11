package com.example.quick_cheque.data.repository

import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.local.entity.RoomEntityWithCheques
import com.example.quick_cheque.data.mapper.toRoom
import com.example.quick_cheque.data.mapper.toRoomEntity
import com.example.quick_cheque.data.remote.QuickChequeApi
import com.example.quick_cheque.domain.model.Room
import com.example.quick_cheque.domain.repository.RoomRepository
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: RoomDao,
    private val quickChequeApi: QuickChequeApi,
) : RoomRepository {
    override suspend fun getMyRooms(
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Room>>> {
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

            val remoteResponse = quickChequeApi.getMyRooms("Dsd") // api key временно

            if (remoteResponse.isSuccessful) {
                emit(Resource.Success(
                    data = remoteResponse.body()!!.map { it.toRoom() }
                ))

                roomDao.clearRooms()
                roomDao.insertRoomList(remoteResponse.body()!!.map { it.toRoomEntity() })
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

    override suspend fun deleteRoom(room: Room) {
        TODO("Not yet implemented")
    }
}