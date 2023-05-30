package com.greenranger.seoulforveggi.view.activity

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.ActivitySignUpBinding
import com.greenranger.seoulforveggi.view.fragment.CountryBottomSheetFragment

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val colorSpan = SpannableStringBuilder(getString(R.string.signup_title)) // 수정된 부분

        // text 색상 변경
        colorSpan.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.green_main)),
            0, // 시작 인덱스
            16, // 끝 인덱스
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.signUpTitle.text = colorSpan

        //bottom sheet
        binding.countrySpinner.setOnClickListener {
            val bottomSheet = CountryBottomSheetFragment()
            supportFragmentManager.setFragmentResultListener(
                "requestKey",
                this
            ) { requestKey, result ->
                val resultCountry = result.getString("bundleKey")
                // Do something with the result...
                binding.myCountry.text = resultCountry
            }
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

}

