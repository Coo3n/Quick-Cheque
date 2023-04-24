package com.example.quick_cheque.data.local.dao

import androidx.room.*
import com.example.quick_cheque.domain.model.Cheque
import kotlinx.coroutines.flow.Flow

@Dao
interface ChequeDao {
    @Query("SELECT * FROM cheque")
    suspend fun getCheques(): Flow<List<Cheque>>?

    @Query("SELECT * FROM cheque WHERE id = :id")
    suspend fun getChequeById(id: Int): Cheque?

    @Insert(entity = Cheque::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheque(cheque: Cheque)

    @Delete(entity = Cheque::class)
    suspend fun deleteCheque(cheque: Cheque)
}