package com.greenranger.seoulforveggi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.greenranger.seoulforveggi.databinding.ActivityMainBinding
import com.greenranger.seoulforveggi.view.HomeFragment
import com.greenranger.seoulforveggi.view.KveganFragment
import com.greenranger.seoulforveggi.view.MapFragment
import com.greenranger.seoulforveggi.view.MypageFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()

        //commit test
//        binding.button.setOnClickListener {
//            val intent = Intent(this, MapActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
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
                        .replace(R.id.main_frm, MapFragment())
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
}