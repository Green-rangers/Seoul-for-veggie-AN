package com.greenranger.seoulforveggi.data.model.response

data class RecommendationResponse(
    val restaurantList: List<Restaurant>
) {
    data class Restaurant(
        val address: String,
        val category: String,
        val distance: Double,
        val id: Int,
        val imageLink: String,
        val isBookmarked: Boolean,
        val name: String
    )
}