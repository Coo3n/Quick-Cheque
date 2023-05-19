package com.example.quick_cheque.data.remote

import com.example.quick_cheque.data.remote.dto.*
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

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
    suspend fun addRoom(
        @Body insertedRoomDto: InsertedRoomDto
    ): Response<InsertedRoomDto>

    @GET("get_rooms")
    @Headers("Content-Type: application/json")
    suspend fun getRooms(): Response<RoomDto>
}

