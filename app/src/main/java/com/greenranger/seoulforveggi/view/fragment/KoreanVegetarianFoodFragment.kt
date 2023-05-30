package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greenranger.seoulforveggi.databinding.FragmentKoreanVegetarianFoodBinding
import com.greenranger.seoulforveggi.retrofit.APIS
import com.greenranger.seoulforveggi.view.base.BaseFragment


class KoreanVegetarianFoodFragment :  BaseFragment<FragmentKoreanVegetarianFoodBinding>() {


    private lateinit var retService: APIS

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentKoreanVegetarianFoodBinding {
        return FragmentKoreanVegetarianFoodBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //retrofit
//        retService = RetrofitClient
//            .getRetrofitInstance()
//            .create(APIS::class.java)
    }

}