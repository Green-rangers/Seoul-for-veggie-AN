package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greenranger.seoulforveggi.databinding.FragmentKveganBinding
import com.greenranger.seoulforveggi.retrofit.APIS
import com.greenranger.seoulforveggi.view.base.BaseFragment

class KveganFragment : BaseFragment<FragmentKveganBinding>() {


    private lateinit var retService: APIS

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentKveganBinding {
        return FragmentKveganBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //retrofit
//        retService = RetrofitClient
//            .getRetrofitInstance()
//            .create(APIS::class.java)

        binding.btnGoSeeMore.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(this.id, KoreanVegetarianFoodFragment())
            transaction?.commit()
        }

        binding.viewall.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(this.id, KoreanRestaurantFragment())
            transaction?.commit()
        }
    }

}