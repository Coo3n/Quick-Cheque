package com.example.quick_cheque.domain.repository

import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.data.remote.dto.AuthenticationResponseDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthenticationRepository {
    suspend fun authenticate(
        authenticationRequestDto: AuthenticationRequestDto
    ): Flow<AuthenticationResponseDto>

    suspend fun register(
        authenticationRequestDto: AuthenticationRequestDto
    ): Flow<AuthenticationResponseDto>
}