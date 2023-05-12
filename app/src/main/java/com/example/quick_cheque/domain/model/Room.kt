package com.example.quick_cheque.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Room(
    val id: Long,
    val title: String,
    val ownerId: Long,
    var membersRoom: MutableList<User>,
    val cntCheques: Int,
) : Parcelable, ChoiceItem {
    override fun getTitleItem(): String {
        return title
    }
}