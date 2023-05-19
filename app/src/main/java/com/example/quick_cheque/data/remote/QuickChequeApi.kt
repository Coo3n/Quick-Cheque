package com.example.quick_cheque.data.remote

import com.example.quick_cheque.data.remote.dto.*
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface QuickChequeApi {
    @POST("login")
    suspend fun authorization(
        @Body authenticationRequest: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto>

    @POST("register")
    suspend fun register(
        @Body authenticationRequest: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto>

    @POST("add_room")
    suspend fun addRoom()

    @POST("get_rooms_admin")
    suspend fun getRooms(): Response<RoomDto>
}