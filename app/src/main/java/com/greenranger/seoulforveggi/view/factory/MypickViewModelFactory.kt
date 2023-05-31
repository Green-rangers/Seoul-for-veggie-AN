package com.greenranger.seoulforveggi.view.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenranger.seoulforveggi.view.viewmodel.MypickViewModel

class MypickViewModelFactory(
    private val application: Application,
    private val accessToken: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MypickViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MypickViewModel(application, accessToken) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}