package com.example.quick_cheque.data.remote

import android.content.SharedPreferences
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = sharedPreferences.getString("API_KEY", "")
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}