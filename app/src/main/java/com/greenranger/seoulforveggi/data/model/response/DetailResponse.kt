package com.greenranger.seoulforveggi.data.model.response

data class DetailResponse(
    val address: String,
    val category: String,
    val id: Int,
    val isBookmarked: Boolean,
    val menu1: String,
    val menu2: String,
    val menu3: String,
    val name: String,
    val openTime: String,
    val phone: String,
    val rating: Double,
    val reviewList: List<Review>
)