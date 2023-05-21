package com.example.quick_cheque.data.repository

import android.content.SharedPreferences
import com.example.quick_cheque.data.local.dao.ChequeDao
import com.example.quick_cheque.data.local.dao.RoomDao
import com.example.quick_cheque.data.local.entity.ChequeEntity
import com.example.quick_cheque.data.local.entity.RoomEntity
import com.example.quick_cheque.data.mapper.toCheque
import com.example.quick_cheque.data.mapper.toChequeEntity
import com.example.quick_cheque.data.mapper.toRoom
import com.example.quick_cheque.data.mapper.toRoomEntity
import com.example.quick_cheque.data.remote.QuickChequeApi
import com.example.quick_cheque.data.remote.dto.ChoiceItemResponseDto
import com.example.quick_cheque.data.remote.dto.InsertedChoiceItem
import com.example.quick_cheque.domain.model.Cheque
import com.example.quick_cheque.domain.repository.ChequeRepository
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChequeRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val chequeDao: ChequeDao,
    private val quickChequeApi: QuickChequeApi,
) : ChequeRepository {
    override suspend fun getCheques(
        choiceItemResponseDto: ChoiceItemResponseDto,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Cheque>>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val localRooms = chequeDao.getCheques()
//                emit(Resource.Success(
//                    data = localRooms.map { it.toCheque() }
//                ))

                val shouldJustLoadOnCache = localRooms.isNotEmpty() && !fetchFromRemote
                if (shouldJustLoadOnCache) {
                    emit(Resource.Loading(false))
                    return@flow
                }

                val remoteResponse = quickChequeApi.getChequesRoom(choiceItemResponseDto)

                if (remoteResponse.isSuccessful) {
                    emit(Resource.Success(
                        data = remoteResponse.body()?.message?.map { it.toCheque() }
                    ))

                    chequeDao.clearCheques()
                    chequeDao.insertChequeList(remoteResponse.body()!!.message!!.map { it.toChequeEntity() })
                } else {
                    emit(Resource.Error(message = "Что-то пошло не так, бро"))
                }

                emit(Resource.Loading(false))
            } catch (exception: Exception) {
                emit(Resource.Error(exception.message))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getChequeById(id: Int): Flow<Resource<Cheque?>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertCheque(cheque: InsertedChoiceItem): Int {
        var insertedChequeId = -1

        try {
            val response = quickChequeApi.addCheque(cheque)

            if (response.isSuccessful) {
                val ownerId = sharedPreferences.getInt("MY_ID", -1)
                insertedChequeId = response.body()?.id!!
                if (ownerId != -1) {
                    val chequeEntity = ChequeEntity(
                        id = insertedChequeId,
                        titleCheque = cheque.title,
                        roomId = cheque.roomId!!,
                        ownerId = ownerId
                    )
                    chequeDao.insertCheque(chequeEntity)
                    return insertedChequeId
                }
            }
        } catch (exception: Exception) {
            return insertedChequeId
        }

        return insertedChequeId
    }

    override suspend fun deleteCheque(cheque: Cheque) {
        TODO("Not yet implemented")
    }
}