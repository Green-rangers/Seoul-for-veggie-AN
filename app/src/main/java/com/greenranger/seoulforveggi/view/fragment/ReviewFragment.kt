package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.FragmentReviewBinding
import com.greenranger.seoulforveggi.retrofit.APIS
import com.greenranger.seoulforveggi.view.base.BaseFragment

class ReviewFragment :  BaseFragment<FragmentReviewBinding>() {

    private lateinit var retService: APIS

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentReviewBinding {
        return FragmentReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //retrofit
//        retService = RetrofitClient
//            .getRetrofitInstance()
//            .create(APIS::class.java)

        binding.star1.setOnClickListener {
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_off)
            binding.star3.setImageResource(R.drawable.ic_star_off)
            binding.star4.setImageResource(R.drawable.ic_star_off)
            binding.star5.setImageResource(R.drawable.ic_star_off)
        }
        binding.star2.setOnClickListener {
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_on)
            binding.star3.setImageResource(R.drawable.ic_star_off)
            binding.star4.setImageResource(R.drawable.ic_star_off)
            binding.star5.setImageResource(R.drawable.ic_star_off)
        }
        binding.star3.setOnClickListener {
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_on)
            binding.star3.setImageResource(R.drawable.ic_star_on)
            binding.star4.setImageResource(R.drawable.ic_star_off)
            binding.star5.setImageResource(R.drawable.ic_star_off)
        }
        binding.star4.setOnClickListener {
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_on)
            binding.star3.setImageResource(R.drawable.ic_star_on)
            binding.star4.setImageResource(R.drawable.ic_star_on)
            binding.star5.setImageResource(R.drawable.ic_star_off)
        }
        binding.star5.setOnClickListener {
            binding.star1.setImageResource(R.drawable.ic_star_on)
            binding.star2.setImageResource(R.drawable.ic_star_on)
            binding.star3.setImageResource(R.drawable.ic_star_on)
            binding.star4.setImageResource(R.drawable.ic_star_on)
            binding.star5.setImageResource(R.drawable.ic_star_on)
        }

        binding.btnSave.setOnClickListener{
            //retrofit

            Toast.makeText(requireContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

}