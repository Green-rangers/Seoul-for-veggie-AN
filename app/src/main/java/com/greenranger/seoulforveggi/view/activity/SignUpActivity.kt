package com.greenranger.seoulforveggi.view.activity

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

    private val countries = resources.getStringArray(R.array.country_array)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val colorSpan = SpannableStringBuilder(R.string.signup_title.toString())

        // text 색상 변경
        colorSpan.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.green_main)),
            0, // 시작 인덱스
            15, // 끝 인덱스
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.signUpTitle.text = colorSpan

        //spanner
        // Set up spinner adapter
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countrySpinner.adapter = myAdapter


//        binding.countrySpinner.setOnClickListener {
//            val bottomSheetFragment = CountryBottomSheetFragment()
//            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
//        }



    }
}

class CountryBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_country_bottom_sheet, container, false)
        // Set up your bottom sheet UI and logic here
        return view
    }
}