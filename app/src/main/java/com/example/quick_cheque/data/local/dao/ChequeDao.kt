package com.example.quick_cheque.data.local.dao

import androidx.room.*
import com.example.quick_cheque.data.local.entity.ChequeEntity
import com.example.quick_cheque.domain.model.Cheque
import kotlinx.coroutines.flow.Flow

@Dao
interface ChequeDao {
    @Query("SELECT * FROM cheque")
    fun getCheques(): List<ChequeEntity>

    @Query("SELECT * FROM cheque WHERE id = :id")
    fun getChequeById(id: Int): ChequeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCheque(cheque: ChequeEntity)

    @Delete
    fun deleteCheque(cheque: ChequeEntity)
}