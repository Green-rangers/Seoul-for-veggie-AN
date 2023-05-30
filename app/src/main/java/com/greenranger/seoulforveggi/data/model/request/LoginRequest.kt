package com.greenranger.seoulforveggi.data.model.request


import androidx.annotation.Keep

@Keep
class LoginRequest (
    private val email: String,
    private val imageLink: String
)