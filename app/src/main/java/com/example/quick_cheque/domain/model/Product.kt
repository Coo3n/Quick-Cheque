package com.example.quick_cheque.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Product(
    val id: Int,
    var titleProduct: String,
    var price: BigDecimal,
    var count: Int,
    var membersProduct: MutableList<User> = mutableListOf()
) : Parcelable, ChoiceItem {
    override fun getTitleItem(): String {
        return titleProduct
    }
}