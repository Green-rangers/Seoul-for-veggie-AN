package com.greenranger.seoulforveggi.data.model.response

data class MyReviewReponse(
    val restaurantList: List<Restaurant>
) {
    data class Restaurant(
        val category: String,
        val content: String,
        val id: Int,
        val imageLink: String,
        val name: String,
        val rating: Double
    )
}