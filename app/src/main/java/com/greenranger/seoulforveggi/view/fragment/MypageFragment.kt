package com.greenranger.seoulforveggi.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.FragmentMypageBinding
import com.greenranger.seoulforveggi.retrofit.APIS
import com.greenranger.seoulforveggi.view.activity.SigninActivity
import com.greenranger.seoulforveggi.view.base.BaseFragment


class MypageFragment : BaseFragment<FragmentMypageBinding>() {

    private lateinit var retService: APIS

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageBinding {
        return FragmentMypageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //retrofit
//        retService = RetrofitClient
//            .getRetrofitInstance()
//            .create(APIS::class.java)

//        binding.goLogin.setOnClickListener {
//            val intent = Intent(activity, SignUpActivity::class.java)
//            startActivity(intent)
//        }

        binding.goLogin.setOnClickListener {
            val intent = Intent(activity, SigninActivity::class.java)
            startActivity(intent)
        }

        //추후 로그인 정보 처리
        if(true) {
            //로드인 정보 없음
            binding.goLogin.visibility = View.VISIBLE
            binding.textView.visibility = View.INVISIBLE
            binding.textView2.visibility = View.INVISIBLE
            binding.myNickName.visibility = View.INVISIBLE
        } else {
            //로그인 정보 있음
            binding.myProfileImage.setImageResource(R.drawable.iv_logo_on)
            binding.goLogin.visibility = View.INVISIBLE
            binding.textView.visibility = View.VISIBLE
            binding.textView2.visibility = View.VISIBLE
            binding.myNickName.visibility = View.VISIBLE
        }



    }

}