package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.data.network.HomeService
import com.greenranger.seoulforveggi.databinding.FragmentRecommendRestaurantBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.adapter.RecommendationViewAdapter
import com.greenranger.seoulforveggi.view.base.BaseFragment
import com.greenranger.seoulforveggi.view.factory.RecommendationViewModelFactory
import com.greenranger.seoulforveggi.view.viewmodel.RecommendationViewModel

class RecommendRestaurantFragment : BaseFragment<FragmentRecommendRestaurantBinding>() {


    private lateinit var retService: HomeService
    private lateinit var viewModel: RecommendationViewModel
    private lateinit var recommendationViewAdapter: RecommendationViewAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecommendRestaurantBinding {
        return FragmentRecommendRestaurantBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myCategory = GlobalApplication.prefs.getString("myCategory", "All")
        val myLatitude = GlobalApplication.prefs.getString("myLatitude", "37").toDoubleOrNull() ?: 37.0
        val myLongitude = GlobalApplication.prefs.getString("myLongitude", "126").toDoubleOrNull() ?: 126.0

        binding.categories.text = myCategory

        //retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(HomeService::class.java)

        // recycler view
        // ViewModel 초기화
        val factory = RecommendationViewModelFactory(
            requireActivity().application,
            myCategory,
            myLatitude,
            myLongitude,
        )
        viewModel = ViewModelProvider(this, factory).get(RecommendationViewModel::class.java)

        recommendationViewAdapter = RecommendationViewAdapter { place ->
            // Click event 처리
            var bundle = Bundle()
            var placeId = place.id
            // openPlaceDetailFragment(placeId)
        }

        // RecyclerView 구성
        binding.recyclerview.adapter = recommendationViewAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())



        // ViewModel과 RecyclerView 어댑터 연결
        viewModel.places.observe(viewLifecycleOwner, Observer { places ->
            places?.let {
                recommendationViewAdapter.updatePlaces(it)
            }
        })

        viewModel.fetchPlaces(myCategory, myLatitude, myLongitude)
    }

}