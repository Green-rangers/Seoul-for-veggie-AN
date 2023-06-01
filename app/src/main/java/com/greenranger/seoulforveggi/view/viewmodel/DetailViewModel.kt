package com.greenranger.seoulforveggi.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.data.model.response.DetailResponse
import com.greenranger.seoulforveggi.data.network.HomeService
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class DetailViewModel(
    application: Application,
    id: Int,
) : AndroidViewModel(application) {

    private lateinit var retService: HomeService
    private val _reviews = MutableLiveData<List<DetailResponse.Review>>()
    val reviews: LiveData<List<DetailResponse.Review>> = _reviews
    val accessToken = GlobalApplication.prefs.getString("userAccessToken", "")

    init {
        fetchReviews(id)
    }

    fun fetchReviews(id: Int) {
        retService = RetrofitClient.getRetrofitInstance().create(HomeService::class.java)

        viewModelScope.launch {
            try {
                val response = retService.restaurantDetail("Bearer $accessToken",id)
                if (response.isSuccessful) {
                    val data = response.body()?.reviewList
                    Log.d("DetailViewModel", "Fetch places success")
                    _reviews.postValue(data!!) // Add this line to update LiveData
                } else {
                    Log.e("DetailViewModel", "Fetch places failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Fetch places failed: ${e.message}")
            }
        }
    }
}
