package com.greenranger.seoulforveggi.data.model.request

data class PostReviewRequest(
    val restaurantId: Int,
    val content: String,
    val rating: Double,
)