package com.greenranger.seoulforveggi.data.network

import com.greenranger.seoulforveggi.data.model.request.LoginRequest
import com.greenranger.seoulforveggi.data.model.request.PostBookmark
import com.greenranger.seoulforveggi.data.model.request.PostReviewRequest
import com.greenranger.seoulforveggi.data.model.request.SignupRequest
import com.greenranger.seoulforveggi.data.model.response.LoginResponse
import com.greenranger.seoulforveggi.data.model.response.NicknameResponse
import com.greenranger.seoulforveggi.data.model.response.PostBookmarkResponse
import com.greenranger.seoulforveggi.data.model.response.SignupResponse
import retrofit2.Response
import retrofit2.http.*

interface LoginService {
    @POST("/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/auth/check")
    suspend fun nicknameCheck(
        @Query("nickname") nickname: String,
    ): Response<NicknameResponse>

    @POST("/auth/signup")
    suspend fun signUp(
        @Header("Authorization") accessToken: String,
        @Body request: SignupRequest
    ): Response<SignupResponse>

    @POST("/api/restaurant/review")
    suspend fun reviewPost(
        @Header("Authorization") accessToken: String,
        @Body request: PostReviewRequest
    )

    @POST("/api/restaurant/review")
    suspend fun bookmarkPost(
        @Header("Authorization") accessToken: String,
        @Body request: PostBookmark
    ): Response<PostBookmarkResponse>

}