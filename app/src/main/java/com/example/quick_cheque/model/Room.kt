package com.example.quick_cheque.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val id: Int,
    val title: String,
    val host: String,
    val users: Int,
    val cheques: Int,
) : Parcelable