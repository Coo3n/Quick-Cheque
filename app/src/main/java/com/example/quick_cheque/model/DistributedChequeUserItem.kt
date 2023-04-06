package com.example.quick_cheque.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DistributedChequeUserItem(
    var user: User,
    var listProductsUser: MutableList<Product>,
    var isExpanded: Boolean = false
) : Parcelable