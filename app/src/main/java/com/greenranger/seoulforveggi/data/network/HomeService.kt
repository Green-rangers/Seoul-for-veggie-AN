package com.greenranger.seoulforveggi.data.network

import com.greenranger.seoulforveggi.data.model.response.CategoryResponse
import com.greenranger.seoulforveggi.data.model.response.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {

    @GET("/api/home")
    suspend fun homeCategory(
        @Query("category") category: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<CategoryResponse>

    @GET("/api/restaurant/{식당id}")
    suspend fun restaurantDetail(
        @Path("id") id: Int
    ): Response<DetailResponse>









}