package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.R
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

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentRecommendRestaurantBinding {
        return FragmentRecommendRestaurantBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myCategory = GlobalApplication.prefs.getString("myCategory", "All")
        val myLatitude = GlobalApplication.prefs.getString("myLatitude", "37")?.toDoubleOrNull() ?: 37.0
        val myLongitude = GlobalApplication.prefs.getString("myLongitude", "126")?.toDoubleOrNull() ?: 126.0
        var myState = GlobalApplication.prefs.getString("myState", "0")
        if (myState == "0") {
            binding.floatingActionButton.visibility = View.GONE
            binding.constraintLayout8.visibility = View.GONE
        } else {
            //맵 버튼 눌렀을 때
            binding.constraintLayout1.visibility = View.VISIBLE
            binding.categories.visibility = View.VISIBLE
            binding.categories.text = "Vegan Restaurants"
            binding.floatingActionButton.visibility = View.VISIBLE
            binding.constraintLayout8.visibility = View.VISIBLE
            binding.floatingActionButton.setOnClickListener {
                GlobalApplication.prefs.setString("myState", "0")
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, NaverMapFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding.categories.text = myCategory
        if(myCategory == " ") {
            binding.categories.text = "Vegan Restaurants"
        }
        Log.d("RecommendRestaurant", "myCategory: $myCategory")

        // Retrofit
        retService = RetrofitClient.getRetrofitInstance().create(HomeService::class.java)

        // RecyclerView
        // ViewModel 초기화
        val factory = RecommendationViewModelFactory(requireActivity().application, myCategory, myLatitude, myLongitude)
        viewModel = ViewModelProvider(this, factory).get(RecommendationViewModel::class.java)

        recommendationViewAdapter = RecommendationViewAdapter { place ->
            // Click event 처리
            val bundle = Bundle()
            val placeId = place.id
            GlobalApplication.prefs.setString("placeId", placeId.toString())
             openPlaceDetailFragment(placeId)
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
