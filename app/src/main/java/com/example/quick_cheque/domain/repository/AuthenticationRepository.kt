package com.example.quick_cheque.domain.repository

import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.data.remote.dto.AuthenticationResponseDto
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthenticationRepository {
    suspend fun authenticate(
        authenticationRequestDto: AuthenticationRequestDto
    ): Flow<Resource<AuthenticationResponseDto>>

    suspend fun register(
        authenticationRequestDto: AuthenticationRequestDto
    ): Flow<Resource<AuthenticationResponseDto>>
}