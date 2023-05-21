package com.example.quick_cheque.data.mapper

import com.example.quick_cheque.R
import com.example.quick_cheque.data.local.entity.ChequeEntity
import com.example.quick_cheque.data.remote.dto.ChequeData
import com.example.quick_cheque.domain.model.Cheque
import java.math.BigDecimal

//fun ChequeEntity.toCheque(): Cheque {
//    return Cheque(
//        id = id!!,
//        title = titleCheque,
//        owner = User(
//            icon = R.drawable.person_filled,
//            id = ownerId,
//            email = ""
//        ),
//        sumOfCheque =
//    )
//}

fun ChequeData.toCheque(): Cheque {
    return Cheque(
        id = id,
        title = title,
        owner = owner.toUser(),
        sumOfCheque = BigDecimal(sumCheque),
        membersCheque = membersCheque.map { it.toUser() }.toMutableList()
    )
}

fun ChequeData.toChequeEntity(): ChequeEntity {
    return ChequeEntity(
        roomId = id,
        ownerId = owner.id,
        titleCheque = title
    )
}

fun Cheque.toChequeEntity(): ChequeEntity {
    return ChequeEntity(
        roomId = id,
        ownerId = owner.id,
        titleCheque = title
    )
}