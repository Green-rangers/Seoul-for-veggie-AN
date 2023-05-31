package com.greenranger.seoulforveggi.data.model.response

data class SignupResponse(
    val country: String,
    val email: String,
    val id: Int,
    val imageLink: String,
    val nickname: String,
    val role: String
)