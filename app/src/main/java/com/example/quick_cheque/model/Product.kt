package com.example.quick_cheque.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
@Parcelize
data class Product(
    var titleProduct: String,
    var price: BigDecimal,
    var count: Int,
    var membersProduct: MutableList<User> = mutableListOf()
) : Parcelable