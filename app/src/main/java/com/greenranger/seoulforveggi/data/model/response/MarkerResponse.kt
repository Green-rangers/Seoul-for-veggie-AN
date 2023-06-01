package com.greenranger.seoulforveggi.data.model.response

data class MarkerResponse(
    val address: String,
    val category: String,
    val distance: Double,
    val id: Int,
    val imageLink: String,
    val name: String
)