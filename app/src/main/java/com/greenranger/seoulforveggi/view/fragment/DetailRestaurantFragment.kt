package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greenranger.seoulforveggi.data.network.HomeService
import com.greenranger.seoulforveggi.databinding.FragmentDetailRestaurantBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailRestaurantFragment : BaseFragment<FragmentDetailRestaurantBinding>() {

    private lateinit var retService: HomeService

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailRestaurantBinding {
        return FragmentDetailRestaurantBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id: Int = 1

        retService = RetrofitClient
            .getRetrofitInstance()
            .create(HomeService::class.java)

        val id_value = arguments?.getInt("id", 1)
        if (id_value != null) {
            id = id_value
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retService.restaurantDetail(id)
                if (response.isSuccessful) {
                    val detailResponse = response.body()
                    // Access the data from detailResponse and update the UI accordingly
                    withContext(Dispatchers.Main) {
                        binding.categories.text = detailResponse?.category
                        binding.tvName.text = detailResponse?.name?.chunked(26)?.joinToString("\n")
                        val formattedContent = detailResponse?.address?.chunked(30)?.joinToString("\n")
                        binding.tvAddress.text = formattedContent
                        binding.tvTime.text = detailResponse?.openTime
                        binding.tvNumber.text = detailResponse?.phone

                        binding.tvMenu1.text = detailResponse?.menu1

                        if (detailResponse?.menu2.isNullOrEmpty()) {
                            binding.tvMenu2.visibility = View.GONE
                        } else {
                            binding.tvMenu2.text = detailResponse?.menu2
                        }

                        if (detailResponse?.menu3.isNullOrEmpty()) {
                            binding.tvMenu3.visibility = View.GONE
                        } else {
                            binding.tvMenu3.text = detailResponse?.menu3
                        }

                        binding.tvRating.text = detailResponse?.rating.toString()

                        if (detailResponse?.reviewList.isNullOrEmpty()) {
                            binding.noReview.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        } else {
                            binding.noReview.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        Log.d("RecommendRestaurant", "Response: ${detailResponse.toString()}")
                    }
                } else {
                    // Handle API call failure
                    Log.d("RecommendRestaurant2", "Failed to fetch restaurant detail")
                }
            } catch (e: Exception) {
                // Handle network error or exception
                Log.d("RecommendRestaurant", "Failed to fetch restaurant detail: ${e.message}")
            }
        }
    }
}
