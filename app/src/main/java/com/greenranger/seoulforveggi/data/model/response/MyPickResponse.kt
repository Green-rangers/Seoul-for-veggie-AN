package com.greenranger.seoulforveggi.data.model.response

data class MyPickResponse(
    val restaurantList: List<Restaurant>
) {
    data class Restaurant(
        val address: String,
        val category: String,
        val id: Int,
        val imageLink: String,
        val name: String
    )
}