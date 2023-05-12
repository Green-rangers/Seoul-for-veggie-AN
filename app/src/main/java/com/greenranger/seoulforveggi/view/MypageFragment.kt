package com.greenranger.seoulforveggi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.greenranger.seoulforveggi.databinding.FragmentMypageBinding
import com.greenranger.seoulforveggi.retrofit.APIS


class MypageFragment : Fragment() {

    private lateinit var binding: FragmentMypageBinding
    private lateinit var retService: APIS

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        //retrofit
//        retService = RetrofitClient
//            .getRetrofitInstance()
//            .create(APIS::class.java)



        return binding.root
    }

}