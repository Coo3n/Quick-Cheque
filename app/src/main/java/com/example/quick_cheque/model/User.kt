package com.example.quick_cheque.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val name: String, val icon: Int) : Parcelable
