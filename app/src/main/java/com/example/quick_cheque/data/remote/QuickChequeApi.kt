package com.example.quick_cheque.data.remote

import com.example.quick_cheque.data.remote.dto.*
import com.google.gson.annotations.SerializedName
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
    suspend fun addRoom()

    @POST("api/get_rooms_admin")
    suspend fun getRooms(@Body token: S): Response<RoomDto>
}

data class S(
    @SerializedName("token")
    val token: String
)