package com.greenranger.seoulforveggi.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greenranger.seoulforveggi.data.model.response.RecommendationResponse
import com.greenranger.seoulforveggi.data.network.HomeService
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class RecommendationViewModel(
    application: Application,
    keyword: String,
    latitude: Double,
    longitude: Double,
) : AndroidViewModel(application) {

    private lateinit var retService: HomeService
    private val _places = MutableLiveData<List<RecommendationResponse.Restaurant>>()
    val places: LiveData<List<RecommendationResponse.Restaurant>> = _places

    init {
        fetchPlaces(keyword, latitude, longitude)
    }

    fun fetchPlaces(keyword: String, latitude: Double, longitude: Double) {
        retService = RetrofitClient.getRetrofitInstance().create(HomeService::class.java)

        viewModelScope.launch {
            try {
                val response = retService.recommendationRestaurant(keyword, latitude, longitude)
                if (response.isSuccessful) {
                    val data = response.body()?.restaurantList
                    Log.d("RecommendationViewModel", "Fetch places success")
                    _places.postValue(data!!) // Add this line to update LiveData
                } else {
                    Log.e("RecommendationViewModel", "Fetch places failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("RecommendationViewModel", "Fetch places failed: ${e.message}")
            }
        }
    }
}
