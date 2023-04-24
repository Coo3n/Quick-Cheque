package com.example.quick_cheque.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
data class Cheque(
    var title: String,
    var owner: User,
    var sumOfCheque: BigDecimal = BigDecimal(0),
    var products: MutableList<Product> = mutableListOf(),
    var membersCheque: MutableList<User> = mutableListOf(),
) : Parcelable, ChoiceItem {
    override fun getTitleItem(): String {
        return title
    }
}
