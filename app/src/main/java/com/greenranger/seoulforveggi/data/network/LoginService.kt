package com.greenranger.seoulforveggi.data.network

import com.greenranger.seoulforveggi.data.model.request.LoginRequest
import com.greenranger.seoulforveggi.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}