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
    @POST("api/login")
    @Headers("Content-Type: application/json")
    suspend fun authorization(
        @Body authenticationRequest: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto>

    @POST("api/register")
    suspend fun register(
        @Body authenticationRequest: AuthenticationRequestDto
    ): Response<AuthenticationResponseDto>

    @POST("api/add_room")
    suspend fun addRoom(
        @Query("token") token: String,
        @Body roomDto: RoomDto
    )

    @POST("api/get_rooms")
    suspend fun getMyRooms(
        @Query("token") token: String,
    ): Response<List<RoomDto>>
}