package com.example.quick_cheque.data.mapper

import com.example.quick_cheque.data.remote.dto.AuthenticationResponseDto
import com.google.gson.Gson
import okhttp3.ResponseBody

fun ResponseBody?.toAuthenticationResponseDto(): AuthenticationResponseDto {
    return Gson().fromJson(this?.string(), AuthenticationResponseDto::class.java)
}