package com.greenranger.seoulforveggi.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.network.MypageService
import com.greenranger.seoulforveggi.databinding.FragmentMypageBinding
import com.greenranger.seoulforveggi.view.activity.MainActivity
import com.greenranger.seoulforveggi.view.activity.SignUpActivity
import com.greenranger.seoulforveggi.view.activity.SigninActivity
import com.greenranger.seoulforveggi.view.base.BaseFragment


class MypageFragment : BaseFragment<FragmentMypageBinding>() {

    private lateinit var retService: MypageService
    private var accessToken = ""
    private var nickname = ""
    private var profileImage = ""
    private var country = ""

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMypageBinding {
        return FragmentMypageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accessToken = GlobalApplication.prefs.getString("userAccessToken", "")
        nickname = GlobalApplication.prefs.getString("userNickname", "")
        profileImage = GlobalApplication.prefs.getString("userProfilePhotoUrl", "")
        country = GlobalApplication.prefs.getString("userCountry", "")


        binding.goLogin.setOnClickListener {
            val intent = Intent(activity, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.myPickLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MyPickFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.myReviewLayout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MyReviewFragment())
                .addToBackStack(null)
                .commit()
        }

        //추후 로그인 정보 처리
        if(accessToken == "") {
            //로드인 정보 없음
            binding.goLogin.visibility = View.VISIBLE
            binding.textView.visibility = View.INVISIBLE
            binding.textView2.visibility = View.INVISIBLE
            binding.myNickName.visibility = View.INVISIBLE
            binding.btnGoLogin.visibility = View.VISIBLE
            binding.btnGoLogout.visibility = View.GONE
            binding.btnGoLogin.setOnClickListener {
                val intent = Intent(activity, SigninActivity::class.java)
                startActivity(intent)
            }
        } else {
            //로그인 정보 있음
            binding.myNickName.text = nickname
            binding.myProfile.text = nickname
            binding.myCountry.text = country
            Glide.with(this)
                .load(profileImage)
                .into(binding.myProfileImage)
            binding.myProfileImage.setImageResource(R.drawable.iv_logo_on)
            binding.btnGoLogin.setImageResource(R.drawable.btn_go_logout)
            binding.goLogin.visibility = View.INVISIBLE
            binding.textView.visibility = View.VISIBLE
            binding.textView2.visibility = View.VISIBLE
            binding.myNickName.visibility = View.VISIBLE
            binding.btnGoLogin.visibility = View.GONE
            binding.btnGoLogout.visibility = View.VISIBLE

            binding.btnGoLogout.setOnClickListener{
                GlobalApplication.prefs.setString("userAccessToken", "")
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(activity, "You are logged out.", Toast.LENGTH_SHORT).show()
            }
        }

    }

}