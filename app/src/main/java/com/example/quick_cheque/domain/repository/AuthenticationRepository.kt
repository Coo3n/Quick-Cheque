package com.example.quick_cheque.domain.repository

import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.data.remote.dto.AuthenticationResponseDto
import retrofit2.Response

interface AuthenticationRepository {
    suspend fun authenticate(
        authenticationRequestDto: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto>

    suspend fun register(
        authenticationRequestDto: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto>
}