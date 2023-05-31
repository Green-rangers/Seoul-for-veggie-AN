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
import com.greenranger.seoulforveggi.databinding.FragmentMyPickBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.adapter.MypickViewAdapter
import com.greenranger.seoulforveggi.view.base.BaseFragment
import com.greenranger.seoulforveggi.view.factory.MypickViewModelFactory
import com.greenranger.seoulforveggi.view.viewmodel.MypickViewModel

class MyPickFragment : BaseFragment<FragmentMyPickBinding>() {

    private lateinit var retService: MypageService
    private var accessToken: String = ""
    private lateinit var viewModel: MypickViewModel
    private lateinit var mypickViewAdapter: MypickViewAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyPickBinding {
        return FragmentMyPickBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accessToken = GlobalApplication.prefs.getString("userAccessToken", "")
//        retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(MypageService::class.java)

        // RecyclerView
        // ViewModel 초기화
        val factory = MypickViewModelFactory(requireActivity().application, accessToken)
        viewModel = ViewModelProvider(this, factory).get(MypickViewModel::class.java)

        mypickViewAdapter = MypickViewAdapter { restaurant ->
            // Click event 처리
            val placeId = restaurant.id
            GlobalApplication.prefs.setString("placeId", placeId.toString())
            openPlaceDetailFragment(placeId)
        }

        // RecyclerView 구성
        binding.recyclerview.adapter = mypickViewAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        // ViewModel과 RecyclerView 어댑터 연결
        viewModel.restaurants.observe(viewLifecycleOwner, Observer { restaurants ->
            restaurants?.let {
                mypickViewAdapter.updateRestaurants(it)
            }
        })

        viewModel.fetchReviews(accessToken)

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