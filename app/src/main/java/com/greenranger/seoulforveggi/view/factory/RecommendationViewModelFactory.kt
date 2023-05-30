package com.greenranger.seoulforveggi.view.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.greenranger.seoulforveggi.view.viewmodel.RecommendationViewModel

class RecommendationViewModelFactory(
    private val application: Application,
    private val keyword: String,
    private val latitude: Double,
    private val longitude: Double,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecommendationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecommendationViewModel(application, keyword, latitude, longitude) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}