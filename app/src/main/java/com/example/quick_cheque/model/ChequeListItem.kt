package com.example.quick_cheque.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ChequeListItem(
    var cheque: Cheque,
    var isExpanded: Boolean = false
) : Parcelable