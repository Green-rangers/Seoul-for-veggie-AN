package com.greenranger.seoulforveggi.data.model.response

data class CategoryResponse(
    val restaurantList: List<Restaurant>
) {
    data class Restaurant(
        val distance: Double,
        val id: Int,
        val imageLink: String,
        val isBookmarked: Boolean,
        val name: String
    )
}