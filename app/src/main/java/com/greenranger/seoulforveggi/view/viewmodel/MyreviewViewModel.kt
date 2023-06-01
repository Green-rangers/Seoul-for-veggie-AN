package com.greenranger.seoulforveggi.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greenranger.seoulforveggi.data.model.response.MyReviewReponse
import com.greenranger.seoulforveggi.data.network.MypageService
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class MyreviewViewModel(
    application: Application,
    accessToken: String,
) : AndroidViewModel(application) {

    private lateinit var retService: MypageService
    private val _restaurants = MutableLiveData<List<MyReviewReponse.Restaurant>>()
    val restaurants: LiveData<List<MyReviewReponse.Restaurant>> = _restaurants

    init {
        fetchReviews(accessToken)
    }

    fun fetchReviews(accessToken: String) {
        retService = RetrofitClient.getRetrofitInstance().create(MypageService::class.java)

        viewModelScope.launch {
            try {
                val response = retService.myReview("Bearer $accessToken")
                if (response.isSuccessful) {
                    val data = response.body()?.restaurantList
                    Log.d("MyreviewViewModel", "Fetch places success")
                    _restaurants.postValue(data!!) // Add this line to update LiveData
                } else {
                    Log.e("MyreviewViewModel", "Fetch places failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MyreviewViewModel", "Fetch places failed: ${e.message}")
            }
        }
    }
}
