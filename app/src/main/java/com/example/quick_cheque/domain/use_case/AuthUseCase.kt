package com.example.quick_cheque.domain.use_case

import android.util.Log
import com.example.quick_cheque.data.mapper.toAuthenticationResponseDto
import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.data.remote.dto.AuthenticationResponseDto
import com.example.quick_cheque.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun authentication(
        authenticationRequestDto: AuthenticationRequestDto
    ): Flow<AuthenticationResponseDto> = flow {
        val response = authenticationRepository.authenticate(
            authenticationRequestDto
        )

        if (response.isSuccessful) {
            Log.i("MyTag", "successful authentication")
            response.body()?.let { emit(it) }
        } else {
            Log.i("MyTag", "UnSuccessful authentication")
            emit(response.errorBody().toAuthenticationResponseDto())
        }
    }

    fun registration(
        authenticationRequestDto: AuthenticationRequestDto
    ): Flow<AuthenticationResponseDto> = flow {
        Log.i("MyTag", authenticationRequestDto.toString())
        val response = authenticationRepository.register(authenticationRequestDto)
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