package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.network.MypageService
import com.greenranger.seoulforveggi.databinding.FragmentMyReviewBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.adapter.MyreviewViewAdapter
import com.greenranger.seoulforveggi.view.base.BaseFragment
import com.greenranger.seoulforveggi.view.factory.MyreviewViewModelFactory
import com.greenranger.seoulforveggi.view.viewmodel.MyreviewViewModel


class MyReviewFragment : BaseFragment<FragmentMyReviewBinding>() {

    private lateinit var retService: MypageService
    private var accessToken: String = ""
    private lateinit var viewModel: MyreviewViewModel
    private lateinit var myreviewViewAdapter: MyreviewViewAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyReviewBinding {
        return FragmentMyReviewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accessToken = GlobalApplication.prefs.getString("userAccessToken", "")
        //retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(MypageService::class.java)

        // RecyclerView
        // ViewModel 초기화
        val factory = MyreviewViewModelFactory(requireActivity().application, accessToken)
        viewModel = ViewModelProvider(this, factory).get(MyreviewViewModel::class.java)

        myreviewViewAdapter = MyreviewViewAdapter { restaurant ->
            // Click event 처리
            val placeId = restaurant.id
            GlobalApplication.prefs.setString("placeId", placeId.toString())
            openPlaceDetailFragment(placeId)
        }

        // RecyclerView 구성
        binding.recyclerview.adapter = myreviewViewAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // ViewModel과 RecyclerView 어댑터 연결
        viewModel.restaurants.observe(viewLifecycleOwner, Observer { restaurants ->
            restaurants?.let {
                myreviewViewAdapter.updateRestaurants(it)
            }
        })

        viewModel.fetchReviews(accessToken)

        binding.imageButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

    private fun openPlaceDetailFragment(id: Int) {
        val detailRestaurantFragment = DetailRestaurantFragment()

        val bundle = Bundle().apply {
            putInt("id", id)
        }
        detailRestaurantFragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frm, detailRestaurantFragment)
            .addToBackStack(null)
            .commit()
    }

}