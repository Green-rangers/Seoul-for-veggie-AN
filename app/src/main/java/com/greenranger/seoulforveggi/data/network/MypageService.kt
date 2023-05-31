package com.greenranger.seoulforveggi.data.network

import com.greenranger.seoulforveggi.data.model.response.MyPickResponse
import com.greenranger.seoulforveggi.data.model.response.MyReviewReponse
import retrofit2.Response
import retrofit2.http.*

interface MypageService {

    @GET("/api/mypage/review")
    suspend fun myReview(
        @Header("Authorization") accessToken: String,
    ): Response<MyReviewReponse>

    @GET("/api/mypage/bookmark")
    suspend fun myPick(
        @Header("Authorization") accessToken: String,
    ): Response<MyPickResponse>

}