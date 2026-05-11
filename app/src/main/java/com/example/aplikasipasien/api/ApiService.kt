package com.example.aplikasipasien.api

import com.example.aplikasipasien.model.LoginRequest
import com.example.aplikasipasien.model.LoginResponse
import com.example.aplikasipasien.model.PasienResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("pasien")
    suspend fun getPasien(
        @Header("Authorization") token: String
    ): Response<PasienResponse>

}