package com.greenranger.seoulforveggi.view.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenranger.seoulforveggi.view.viewmodel.DetailViewModel

class DetailViewModelFactory(
    private val application: Application,
    private val id: Int,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(application, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}