package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greenranger.seoulforveggi.data.network.MypageService
import com.greenranger.seoulforveggi.databinding.FragmentMyReviewBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.adapter.DetailViewAdapter
import com.greenranger.seoulforveggi.view.base.BaseFragment
import com.greenranger.seoulforveggi.view.viewmodel.DetailViewModel


class MyReviewFragment : BaseFragment<FragmentMyReviewBinding>() {

    private lateinit var retService: MypageService
    private var accessToken: String = ""
    private lateinit var viewModel: DetailViewModel
    private lateinit var detailViewAdapter: DetailViewAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyReviewBinding {
        return FragmentMyReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(MypageService::class.java)





    }

}