package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greenranger.seoulforveggi.databinding.FragmentDetailRestaurantBinding
import com.greenranger.seoulforveggi.retrofit.APIS
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.base.BaseFragment

class DetailRestaurantFragment : BaseFragment<FragmentDetailRestaurantBinding>() {


    private lateinit var retService: APIS


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailRestaurantBinding {
        return FragmentDetailRestaurantBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var id: Int = 1
//        retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(APIS::class.java)

        val id_value = arguments?.getInt("id", 1)
        if (id_value != null) {
            id = id_value
        }
    }

}