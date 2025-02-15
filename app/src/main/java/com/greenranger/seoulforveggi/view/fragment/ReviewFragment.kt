package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.load.HttpException
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.model.request.PostReviewRequest
import com.greenranger.seoulforveggi.data.network.HomeService
import com.greenranger.seoulforveggi.databinding.FragmentReviewBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.base.BaseFragment

class ReviewFragment :  BaseFragment<FragmentReviewBinding>() {

    private lateinit var retService: HomeService
    private var InputReview = ""
    private var rating = 0.0
    private var accessToken = ""
    private var id = 0

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReviewBinding {
        return FragmentReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accessToken = GlobalApplication.prefs.getString("userAccessToken", "")
        id = GlobalApplication.prefs.getString("placeId", "1")!!.toInt()

        view.setOnTouchListener { v, event ->
            hideKeyboard(v)
            false
        }

        //retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(HomeService::class.java)

        binding.star1.setOnClickListener {
            rating = 1.0
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_off)
            binding.star3.setImageResource(R.drawable.ic_star_off)
            binding.star4.setImageResource(R.drawable.ic_star_off)
            binding.star5.setImageResource(R.drawable.ic_star_off)
        }
        binding.star2.setOnClickListener {
            rating = 2.0
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_on)
            binding.star3.setImageResource(R.drawable.ic_star_off)
            binding.star4.setImageResource(R.drawable.ic_star_off)
            binding.star5.setImageResource(R.drawable.ic_star_off)
        }
        binding.star3.setOnClickListener {
            rating = 3.0
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_on)
            binding.star3.setImageResource(R.drawable.ic_star_on)
            binding.star4.setImageResource(R.drawable.ic_star_off)
            binding.star5.setImageResource(R.drawable.ic_star_off)
        }
        binding.star4.setOnClickListener {
            rating = 4.0
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_on)
            binding.star3.setImageResource(R.drawable.ic_star_on)
            binding.star4.setImageResource(R.drawable.ic_star_on)
            binding.star5.setImageResource(R.drawable.ic_star_off)
        }
        binding.star5.setOnClickListener {
            rating = 5.0
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_on)
            binding.star3.setImageResource(R.drawable.ic_star_on)
            binding.star4.setImageResource(R.drawable.ic_star_on)
            binding.star5.setImageResource(R.drawable.ic_star_on)
        }

        binding.btnSave.setOnClickListener{
            if(accessToken == ""){
                Toast.makeText(requireContext(), "Login is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                //retrofit
                binding.apply {
                    InputReview = reviewText.text.toString()
                }
                lifecycleScope.launchWhenCreated {
                    postReview(accessToken, id, InputReview, rating)
                }
            }
        }

    }


    suspend fun postReview(accessToken: String, restaurantId: Int, content: String, rating: Double) {
        try {
            val requestBody = PostReviewRequest(restaurantId, content, rating)
            retService.reviewPost("Bearer $accessToken", requestBody)

            // If we get here, then the post was a success
            Log.d("리뷰 통신 성공","리뷰 post 통신 성공, 요청 성공")
            Toast.makeText(requireContext(), "Post Success", Toast.LENGTH_SHORT).show()

            // Home으로 이동
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, DetailRestaurantFragment())
                .addToBackStack(null)
                .commit()

        } catch (e: HttpException) {
            // We had a non-2XX HTTP error
            Toast.makeText(requireContext(), "Post failed", Toast.LENGTH_SHORT).show()
            Log.d("리뷰 post 통신 실패", "요청 실패: " + e.message)

        } catch (e: Exception) {
            // A network error happened
            Toast.makeText(requireContext(), "Post failed", Toast.LENGTH_SHORT).show()
            Log.d("리뷰 post 통신 실패", "전송 실패")
        }
    }


    // 키보드 숨기기
    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(requireContext(), InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }


}