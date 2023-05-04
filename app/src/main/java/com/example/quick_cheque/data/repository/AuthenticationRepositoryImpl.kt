package com.example.quick_cheque.data.repository

import com.example.quick_cheque.data.remote.QuickChequeApi
import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.data.remote.dto.AuthenticationResponseDto
import com.example.quick_cheque.domain.repository.AuthenticationRepository
import retrofit2.Response
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val quickChequeApi: QuickChequeApi
) : AuthenticationRepository {
    override suspend fun authenticate(
        authenticationRequestDto: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto> {
        return quickChequeApi.authorization(authenticationRequestDto)
    }

    override suspend fun register(
        authenticationRequestDto: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto> {
        return quickChequeApi.register(authenticationRequestDto)
    }
}