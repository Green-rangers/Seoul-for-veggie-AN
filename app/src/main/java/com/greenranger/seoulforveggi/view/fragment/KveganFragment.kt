package com.greenranger.seoulforveggi.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.network.HomeService
import com.greenranger.seoulforveggi.databinding.FragmentKveganBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class KveganFragment : BaseFragment<FragmentKveganBinding>() {

    private lateinit var retService: HomeService

    var id1: Int = 1
    var id2: Int = 1
    var id3: Int = 1
    var id4: Int = 1

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentKveganBinding {
        return FragmentKveganBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myLatitude = GlobalApplication.prefs.getString("myLatitude", "37").toDouble()
        val myLongitude = GlobalApplication.prefs.getString("myLongitude", "126").toDouble()
        Log.d("K-vegan", "myLatitude: $myLatitude, myLongitude: $myLongitude")
        GlobalApplication.prefs.setString("myCategory", "KoreanFood")

        //retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(HomeService::class.java)

        binding.btnGoSeeMore.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(this.id, KoreanVegetarianFoodFragment())
            transaction?.commit()
        }

        binding.viewall.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(this.id, RecommendRestaurantFragment())
            transaction?.commit()
        }

        val category = "KoreanFood"
        callRestaurantListAPI(category, myLatitude, myLongitude)

        //detailFragment로 이동
        binding.cardView.setOnClickListener {
            openRestaurantRecommendationFragment(id1)

        }
        binding.cardView2.setOnClickListener {
            openRestaurantRecommendationFragment(id2)
        }
        binding.cardView3.setOnClickListener {
            openRestaurantRecommendationFragment(id3)
        }
        binding.cardView4.setOnClickListener {
            openRestaurantRecommendationFragment(id4)
        }
    }

    private fun callRestaurantListAPI(category: String, latitude: Double, longitude: Double){
        lifecycleScope.launch {
            try {
                val response = retService.homeCategory(category, latitude, longitude)
                if (response.isSuccessful) {
                    Log.d("Home", "Success, category: $category, latitude: $latitude, longitude: $longitude")
                    val restaurantListData = response.body()?.restaurantList
                    // Data processing
                    Log.d("Home", "${restaurantListData.toString()}")

                    val chunkSize = 17

                    //item1
                    //distance
                    binding.distance.text = "${restaurantListData?.get(0)?.distance}"
                    //id
                    id1 = restaurantListData?.get(0)?.id!!.toInt()

                    //image
                    if(restaurantListData?.get(0)?.imageLink != null){
                        Picasso.get()
                            .load(restaurantListData[0].imageLink)
                            .into(binding.restaurantImage1)
                    }

                    //heart
                    if(restaurantListData!!.get(0).isBookmarked) {
                        binding.heart1.setImageResource(R.drawable.ic_heart_on)
                    } else {
                        binding.heart1.setImageResource(R.drawable.ic_heart)
                    }

                    //name
                    var contentValue= restaurantListData?.get(0)?.name
                    val formattedContent = contentValue?.chunked(chunkSize)?.joinToString("\n")
                    binding.restaurantsName1.text = formattedContent

                    //item2
                    //distance
                    binding.distance2.text = "${restaurantListData?.get(1)?.distance}"
                    //id
                    id2 = restaurantListData?.get(1)?.id!!.toInt()

                    //image
                    if(restaurantListData?.get(1)?.imageLink != null){
                        Picasso.get()
                            .load(restaurantListData[1].imageLink)
                            .into(binding.restaurantImage2)
                    }

                    //heart
                    if(restaurantListData!!.get(1).isBookmarked) {
                        binding.heart2.setImageResource(R.drawable.ic_heart_on)
                    } else {
                        binding.heart2.setImageResource(R.drawable.ic_heart)
                    }

                    //name
                    var contentValue2= restaurantListData?.get(1)?.name
                    val formattedContent2 = contentValue2?.chunked(chunkSize)?.joinToString("\n")
                    binding.restaurantsName2.text = formattedContent2

                    //item3
                    //distance
                    binding.distance3.text = "${restaurantListData?.get(2)?.distance}"
                    //id
                    id3 = restaurantListData?.get(2)?.id!!.toInt()

                    //image
                    if(restaurantListData?.get(2)?.imageLink != null){
                        Picasso.get()
                            .load(restaurantListData[2].imageLink)
                            .into(binding.restaurantImage3)
                    }

                    //heart
                    if(restaurantListData!!.get(2).isBookmarked) {
                        binding.heart3.setImageResource(R.drawable.ic_heart_on)
                    } else {
                        binding.heart3.setImageResource(R.drawable.ic_heart)
                    }

                    //name
                    var contentValue3 = restaurantListData?.get(2)?.name
                    val formattedContent3 = contentValue3?.chunked(chunkSize)?.joinToString("\n")
                    binding.restaurantsName3.text = formattedContent3

                    //item4
                    //distance
                    binding.distance4.text = "${restaurantListData?.get(3)?.distance}"
                    //id
                    id4 = restaurantListData?.get(3)?.id!!.toInt()

                    //image
                    if(restaurantListData?.get(3)?.imageLink != null){
                        Picasso.get()
                            .load(restaurantListData[3].imageLink)
                            .into(binding.restaurantImage4)
                    }

                    //heart
                    if(restaurantListData!!.get(3).isBookmarked) {
                        binding.heart4.setImageResource(R.drawable.ic_heart_on)
                    } else {
                        binding.heart4.setImageResource(R.drawable.ic_heart)
                    }

                    //name
                    var contentValue4 = restaurantListData?.get(3)?.name
                    val formattedContent4 = contentValue4?.chunked(chunkSize)?.joinToString("\n")
                    binding.restaurantsName4.text = formattedContent4



                } else {
                    // Error handling
                    Log.e("Home 실패", "Error: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                // Exception handling
                Log.e("Home 실패2", "Exception: ${e.message}")
            }
        }
    }

    private fun openRestaurantRecommendationFragment(id: Int) {
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