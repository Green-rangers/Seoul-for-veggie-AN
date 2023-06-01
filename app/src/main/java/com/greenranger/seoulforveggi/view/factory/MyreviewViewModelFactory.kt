package com.greenranger.seoulforveggi.view.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenranger.seoulforveggi.view.viewmodel.MyreviewViewModel

class MyreviewViewModelFactory(
    private val application: Application,
    private val accessToken: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyreviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyreviewViewModel(application, accessToken) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}