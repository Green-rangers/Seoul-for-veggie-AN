package com.greenranger.seoulforveggi.data.network

import com.greenranger.seoulforveggi.data.model.request.PostBookmark
import com.greenranger.seoulforveggi.data.model.request.PostReviewRequest
import com.greenranger.seoulforveggi.data.model.response.CategoryResponse
import com.greenranger.seoulforveggi.data.model.response.DetailResponse
import com.greenranger.seoulforveggi.data.model.response.PostBookmarkResponse
import com.greenranger.seoulforveggi.data.model.response.RecommendationResponse
import retrofit2.Response
import retrofit2.http.*

interface HomeService {

    @GET("/api/home")
    suspend fun homeCategory(
        @Query("category") category: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<CategoryResponse>

    @GET("/api/home/search")
    suspend fun recommendationRestaurant(
        @Query("keyword") keyword: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<RecommendationResponse>

    @GET("/api/restaurant/{id}")
    suspend fun restaurantDetail(
        @Path("id") id: Int
    ): Response<DetailResponse>

    @POST("/api/restaurant/review")
    suspend fun reviewPost(
        @Header("Authorization") accessToken: String,
        @Body request: PostReviewRequest
    )

    @POST("/api/restaurant/bookmark")
    suspend fun bookmarkPost(
        @Header("Authorization") accessToken: String,
        @Body request: PostBookmark
    ): Response<PostBookmarkResponse>







}