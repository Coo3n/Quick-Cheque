package com.example.quick_cheque.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChequeListItem(
    val cheque: Cheque,
    var isClicked: Boolean = false,
    var isExpanded: Boolean = false
) : ChoiceItem, Parcelable {
    override fun getTitleItem(): String {
        return cheque.title
    }
}
