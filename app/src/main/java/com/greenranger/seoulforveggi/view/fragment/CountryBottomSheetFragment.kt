package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.FragmentCountryBottomSheetBinding

class CountryBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentCountryBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_country_bottom_sheet, container, false)

        val countryOption1: TextView = view.findViewById(R.id.country_option_1)
        val countryOption2: TextView = view.findViewById(R.id.country_option_2)

        countryOption1.setOnClickListener {
            setFragmentResult("requestKey", bundleOf("bundleKey" to "Country 1"))
            dismiss()
        }

        countryOption2.setOnClickListener {
            setFragmentResult("requestKey", bundleOf("bundleKey" to "Country 2"))
            dismiss()
        }

        // Add more options as needed
        return view
    }
}