package com.greenranger.seoulforveggi.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.ActivityMainBinding
import com.greenranger.seoulforveggi.view.fragment.HomeFragment
import com.greenranger.seoulforveggi.view.fragment.KveganFragment
import com.greenranger.seoulforveggi.view.fragment.MypageFragment
import com.greenranger.seoulforveggi.view.fragment.NaverMapFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.kveganFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, KveganFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.mapFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, NaverMapFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MypageFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onBackPressed() {
        // 뒤로 가기를 눌렀을 때의 동작을 여기에 구현합니다.
        // 예를 들어, 앱을 종료하지 않고 홈 화면으로 이동하도록 설정할 수 있습니다.
        moveTaskToBack(true)
    }
}