package com.example.quick_cheque.data.remote

import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.data.remote.dto.AuthenticationResponseDto
import com.example.quick_cheque.data.remote.dto.RoomDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface QuickChequeApi {
    @POST("/login")
    @Headers("Content-Type: application/json")
    suspend fun authorization(
        @Body authenticationRequest: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto>

    @POST("/register")
    suspend fun register(
        @Body authenticationRequest: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto>

    @POST("/add_room")
    suspend fun addRoom(
        @Query("token") token: String,
        @Body roomDto: RoomDto
    )
}