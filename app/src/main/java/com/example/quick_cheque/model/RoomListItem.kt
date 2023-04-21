package com.example.quick_cheque.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RoomListItem(
    var room: Room,
    var isExpanded: Boolean = false,
) : Parcelable, ChoiceItem {
    override fun getTitleItem(): String {
        return room.title
    }
}