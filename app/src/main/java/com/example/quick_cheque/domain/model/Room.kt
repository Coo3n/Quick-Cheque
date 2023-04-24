package com.example.quick_cheque.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val id: Int,
    val title: String,
    val host: String,
    var membersRoom: MutableList<User>,
    val cheques: MutableList<Cheque>,
) : Parcelable, ChoiceItem {
    override fun getTitleItem(): String {
        return title
    }
}