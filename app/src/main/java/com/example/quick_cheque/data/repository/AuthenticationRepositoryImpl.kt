package com.example.quick_cheque.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.example.quick_cheque.data.mapper.toAuthenticationResponseDto
import com.example.quick_cheque.data.remote.QuickChequeApi
import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.data.remote.dto.AuthenticationResponseDto
import com.example.quick_cheque.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val quickChequeApi: QuickChequeApi
) : AuthenticationRepository {
    override suspend fun authenticate(
        authenticationRequestDto: AuthenticationRequestDto
    ): Flow<AuthenticationResponseDto> = flow {
        val response = quickChequeApi.authorization(
            authenticationRequestDto
        )

        if (response.isSuccessful) {
            Log.i("MyTag", "successful authentication")
            sharedPreferences.edit().apply {
                putInt("MY_ID", response.body()?.id!!)
                putString("API_KEY", response.body()?.token)
                apply()
            }
            response.body()?.let { emit(it) }
        } else {
            Log.i("MyTag", "UnSuccessful authentication")
            emit(response.errorBody().toAuthenticationResponseDto())
        }
    }

    override suspend fun register(
        authenticationRequestDto: AuthenticationRequestDto
    ): Flow<AuthenticationResponseDto> = flow {
        val response = quickChequeApi.register(authenticationRequestDto)
        Log.i("MyTag", response.code().toString())

        if (response.isSuccessful) {
            Log.i("MyTag", "successful registration")
            response.body()?.let { emit(it) }
        } else {
            Log.i("MyTag", "UnSuccessful registration")
            emit(response.errorBody().toAuthenticationResponseDto())
        }
    }
}