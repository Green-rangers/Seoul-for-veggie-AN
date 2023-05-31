package com.greenranger.seoulforveggi.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.FragmentCountryBottomSheetBinding

class CountryBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCountryBottomSheetBinding

    private val handler = Handler(Looper.getMainLooper())
    private val delayDuration = 500L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBottomSheetBinding.inflate(inflater, container, false)

        setItemClickListeners()

        return binding.root
    }

    private fun setItemClickListeners() {
        val countries = arrayOf(
            "New Zealand", "Taiwan", "South Korea", "Germany", "Russia", "United States",
            "Vietnam", "Spain", "Singapore", "United Arab Emirates", "United Kingdom",
            "India", "Japan", "China", "Kazakhstan", "Canada", "Thailand", "France",
            "Philippines", "Australia", "Hong Kong"
        )

        for (country in countries) {
            val textViewId = country.replace(" ", "_")
            val textView = binding.root.findViewById<TextView>(resources.getIdentifier(textViewId, "id", requireContext().packageName))
            textView.setOnClickListener { handleItemClick(textView) }
        }
    }

    private fun handleItemClick(textView: TextView) {
        val backgroundColor = ContextCompat.getColor(requireContext(), R.color.green_background)
        val textColor = ContextCompat.getColor(requireContext(), R.color.green_main)

        // 배경색과 텍스트 색상 변경
        textView.setBackgroundColor(backgroundColor)
        textView.setTextColor(textColor)

        // 1초 후 결과 전달
        handler.postDelayed({
            // 선택된 나라의 이름을 전달
            val bundle = bundleOf("bundleKey" to textView.text.toString())
            setFragmentResult("requestKey", bundle)

            dismiss()
        }, delayDuration)
    }

    override fun onDestroyView() {
        // 핸들러 메시지 제거 (메모리 누수 방지)
        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // BottomSheetBehavior 설정
        dialog.behavior.apply {
            // 크기 고정
            peekHeight = resources.getDimensionPixelSize(R.dimen.bottom_sheet_height)
            // 스크롤 불가능
            isDraggable = true
            // 터치 외의 영역 터치 시 BottomSheetDialog 닫기
            setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        state = BottomSheetBehavior.STATE_COLLAPSED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }

        return dialog
    }
}