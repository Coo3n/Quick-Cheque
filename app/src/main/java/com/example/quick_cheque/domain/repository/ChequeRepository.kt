package com.example.quick_cheque.domain.repository

import com.example.quick_cheque.data.remote.dto.ChoiceItemResponseDto
import com.example.quick_cheque.data.remote.dto.InsertedChoiceItem
import com.example.quick_cheque.domain.model.Cheque
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChequeRepository {
    suspend fun getCheques(
        choiceItemResponseDto: ChoiceItemResponseDto,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Cheque>>>

    suspend fun getChequeById(id: Int): Flow<Resource<Cheque?>>

    suspend fun insertCheque(cheque: InsertedChoiceItem): Int

    suspend fun deleteCheque(cheque: Cheque)
}