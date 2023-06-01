package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.model.request.PostBookmark
import com.greenranger.seoulforveggi.data.network.HomeService
import com.greenranger.seoulforveggi.databinding.FragmentDetailRestaurantBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.adapter.DetailViewAdapter
import com.greenranger.seoulforveggi.view.base.BaseFragment
import com.greenranger.seoulforveggi.view.factory.DetailViewModelFactory
import com.greenranger.seoulforveggi.view.viewmodel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailRestaurantFragment : BaseFragment<FragmentDetailRestaurantBinding>() {

    private lateinit var retService: HomeService
    private var accessToken: String = ""
    private lateinit var viewModel: DetailViewModel
    private lateinit var detailViewAdapter: DetailViewAdapter

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDetailRestaurantBinding {
        return FragmentDetailRestaurantBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id: Int = 1
        accessToken = GlobalApplication.prefs.getString("userAccessToken", "")
        id = GlobalApplication.prefs.getString("placeId","1").toInt()

        retService = RetrofitClient
            .getRetrofitInstance()
            .create(HomeService::class.java)

        binding.review.setOnClickListener{
            val reviewFragment = ReviewFragment()
            val bundle = Bundle()
            bundle.putInt("id", id)
            reviewFragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, reviewFragment).addToBackStack(null).commit()
        }

        val id_value = arguments?.getInt("id", 1)
        if (id_value != null) {
            id = id_value
        }
        GlobalApplication.prefs.setString("placeId", id.toString())


        binding.bookmark.setOnClickListener {
            if(accessToken == "") {
                Toast.makeText(context, "Login is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                lifecycleScope.launchWhenCreated {
                    postBookmark(accessToken, id)
                }
            }
        }

        // RecyclerView
        // ViewModel 초기화
        val factory = DetailViewModelFactory(requireActivity().application, id)
        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)

        detailViewAdapter = DetailViewAdapter { review ->
            // Click event 처리

        }

        // RecyclerView 구성
        binding.recyclerView.adapter = detailViewAdapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager

        // ViewModel과 RecyclerView 어댑터 연결
        viewModel.reviews.observe(viewLifecycleOwner, Observer { reviews ->
            reviews?.let {
                detailViewAdapter.updateReviews(it)
            }
        })

        viewModel.fetchReviews(id)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = retService.restaurantDetail("Bearer $accessToken",id)
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

                        binding.tvMenu1.text = detailResponse?.menu1?.chunked(30)?.joinToString("\n")

                        if (!detailResponse?.imageLink.isNullOrEmpty()) {
                            Glide.with(view)
                                .load(detailResponse?.imageLink)
                                .into(binding.imageView9)
                        }

                        if(detailResponse?.isBookmarked == true) {
                            binding.bookmark.setImageResource(R.drawable.ic_heart_on)
                        } else {
                            binding.bookmark.setImageResource(R.drawable.ic_heart)
                        }

                        if (detailResponse?.menu2.isNullOrEmpty()) {
                            binding.tvMenu2.visibility = View.GONE
                        } else {
                            binding.tvMenu2.text = detailResponse?.menu2?.chunked(30)?.joinToString("\n")
                        }

                        if (detailResponse?.menu3.isNullOrEmpty()) {
                            binding.tvMenu3.visibility = View.GONE
                        } else {
                            binding.tvMenu3.text = detailResponse?.menu3?.chunked(30)?.joinToString("\n")
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

    suspend fun postBookmark(accessToken: String, restaurantId: Int) {
        try {
            Log.d("북마크 post 통신 시작", "$accessToken, $restaurantId")
            val requestBody = PostBookmark(restaurantId)
            val response = retService.bookmarkPost("Bearer $accessToken", requestBody)

            if (response.isSuccessful) {
                // 요청 성공
                val PostBookmarkResponse = response.body()
                Log.d("북마크 post 통신 성공, 요청 성공", response.toString())

                if (PostBookmarkResponse?.isBookmarked == true) {
                    binding.bookmark.setImageResource(R.drawable.ic_heart_on)
                } else {
                    binding.bookmark.setImageResource(R.drawable.ic_heart)
                }
            } else {
                // 요청 실패
                Toast.makeText(requireContext(), "Post failed", Toast.LENGTH_SHORT).show()
                Log.d("북마크 post 통신 성공, 요청 실패", response.toString())
                Log.d("북마크 post  통신 성공, 요청 실패", response.body().toString())
            }
        } catch (e: Exception) {
            // 네트워크 에러 또는 예외
            Toast.makeText(requireContext(), "Post failed", Toast.LENGTH_SHORT).show()
            Log.d("북마크 post 통신 실패", "전송 실패")
        }
    }
}
