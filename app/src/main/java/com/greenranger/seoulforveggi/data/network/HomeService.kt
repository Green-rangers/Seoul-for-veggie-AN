package com.greenranger.seoulforveggi.data.network

import com.greenranger.seoulforveggi.data.model.request.PostBookmark
import com.greenranger.seoulforveggi.data.model.request.PostReviewRequest
import com.greenranger.seoulforveggi.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface HomeService {

    @GET("/api/home")
    suspend fun homeCategory(
        @Header("Authorization") accessToken: String,
        @Query("category") category: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<CategoryResponse>

    @GET("/api/home/search")
    suspend fun recommendationRestaurant(
        @Header("Authorization") accessToken: String,
        @Query("keyword") keyword: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<RecommendationResponse>

    @GET("/api/restaurant/{id}")
    suspend fun restaurantDetail(
        @Header("Authorization") accessToken: String,
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

    @GET("/api/map")
    suspend fun getMap(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<MapResponse>

    @GET("/api/map/restaurant/{id}?")
    suspend fun getMarker(
        @Path("id") id: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<MarkerResponse>

}