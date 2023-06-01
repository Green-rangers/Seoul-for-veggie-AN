package com.greenranger.seoulforveggi.view.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.greenranger.seoulforveggi.GlobalApplication
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.network.HomeService
import com.greenranger.seoulforveggi.databinding.FragmentHomeBinding
import com.greenranger.seoulforveggi.retrofit.RetrofitClient
import com.greenranger.seoulforveggi.view.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var retService: HomeService

    // 선택된 ConstraintLayout을 저장할 변수
    private var selectedView: ConstraintLayout? = null

    //위치 정보 관리자
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 124
    var homelatitude: Double = 37.56497068
    var homelongitude: Double = 126.90364603
    var id1: Int = 1
    var id2: Int = 1
    var id3: Int = 1
    var id4: Int = 1
    private var category: String = "a"
    private var accessToken = GlobalApplication.prefs.getString("userAccessToken", "")

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var detailFragment: DetailRestaurantFragment? = null
        GlobalApplication.prefs.setString("myCategory", " ")
        GlobalApplication.prefs.setString("myState", "0")

        // 위치 관리자 초기화
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // 위치 권한 확인 & 현위치 서버로 보내주기
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 위치 권한이 허용되지 않은 경우 권한 요청
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        } else {
            // 위치 권한이 이미 허용된 경우 위치 업데이트 시작
            startLocationUpdates()
        }

        //retrofit
        retService = RetrofitClient
            .getRetrofitInstance()
            .create(HomeService::class.java)

        callRestaurantListAPI("All", homelatitude, homelongitude)

        //검색
        binding.searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val enteredText = binding.searchText.text.toString()
                // 여기서 enteredText를 저장하거나 처리합니다.
                // 예를 들어, ViewModel이나 SharedPreferences를 사용하여 저장할 수 있습니다.
//                Toast.makeText(requireContext(), "입력된 값: $enteredText", Toast.LENGTH_SHORT).show()
                GlobalApplication.prefs.setString("myCategory", enteredText)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, RecommendRestaurantFragment())
                    .addToBackStack(null)
                    .commit()

                return@setOnEditorActionListener true
            }
            false
        }


        //all 카테고리 선택으로 초기화
        binding.all.setBackgroundResource(R.drawable.bg_home_item_radius_click)
        binding.allText.setTextColor(Color.parseColor("#000000"))

//      각 ConstraintLayout에 클릭 리스너를 등록합니다.
        binding.all.setOnClickListener {
            onViewClicked(binding.all)
            binding.categories.text = "Nearby"
            //서버에 요청
            category = "All"
            callRestaurantListAPI(category, homelatitude, homelongitude)
            GlobalApplication.prefs.setString("myCategory", " ")
        }
        binding.chinese.setOnClickListener {
            onViewClicked(binding.chinese)
            binding.categories.text = "Chinese Food"
            //서버에 요청
            category = "Chinese Food"
            callRestaurantListAPI(category, homelatitude, homelongitude)
            GlobalApplication.prefs.setString("myCategory", "Chinese Food")
        }
        binding.western.setOnClickListener {
            onViewClicked(binding.western)
            binding.categories.text = "Western Food"
            //서버에 요청
            category = "Western Food"
            callRestaurantListAPI(category, homelatitude, homelongitude)
            GlobalApplication.prefs.setString("myCategory", "Western Food")
        }
        binding.asian.setOnClickListener {
            onViewClicked(binding.asian)
            binding.categories.text = "Asian Food"
            //서버에 요청
            category = "Asian Food"
            callRestaurantListAPI(category, homelatitude, homelongitude)
            GlobalApplication.prefs.setString("myCategory", "Asian Food")
        }
        binding.eastern.setOnClickListener {
            onViewClicked(binding.eastern)
            binding.categories.text = "Middle Eastern"
            //서버에 요청
            category = "Middle Eastern"
            callRestaurantListAPI(category, homelatitude, homelongitude)
            GlobalApplication.prefs.setString("myCategory", "Middle Eastern")
        }
        binding.bakery.setOnClickListener {
            onViewClicked(binding.bakery)
            binding.categories.text = "Bakery"
            //서버에 요청
            category = "Bakery"
            callRestaurantListAPI(category, homelatitude, homelongitude)
            GlobalApplication.prefs.setString("myCategory", "Bakery")
        }
        binding.cafe.setOnClickListener {
            onViewClicked(binding.cafe)
            binding.categories.text = "Cafe"
            //서버에 요청
            category = "Cafe"
            callRestaurantListAPI(category, homelatitude, homelongitude)
            GlobalApplication.prefs.setString("myCategory", "Cafe")
        }
        binding.mexican.setOnClickListener {
            onViewClicked(binding.mexican)
            binding.categories.text = "Mexican Food"
            //서버에 요청
            category = "Mexican Food"
            callRestaurantListAPI(category, homelatitude, homelongitude)
            GlobalApplication.prefs.setString("myCategory", "Mexican Food")
        }
        binding.salad.setOnClickListener {
            onViewClicked(binding.salad)
            binding.categories.text = "Salad"
            //서버에 요청
            category = "Salad"
            callRestaurantListAPI(category, homelatitude, homelongitude)
            GlobalApplication.prefs.setString("myCategory", "Salad")
        }

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

        binding.viewAll.setOnClickListener{
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(this.id, RecommendRestaurantFragment())
            transaction?.addToBackStack(null)
            transaction?.commit()
        }

    }

    private fun startLocationUpdates() {
        // 위치 업데이트 리스너 등록
        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                // 위치 정보 업데이트 처리
                handleLocationChange(location)

                // 위치 정보
                Log.d("Home : MyLocation", "Latitude: $homelatitude, Longitude: $homelongitude")
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                // 상태 변경 이벤트 처리
            }

            override fun onProviderEnabled(provider: String) {
                // 위치 공급자 활성화 이벤트 처리
            }

            override fun onProviderDisabled(provider: String) {
                // 위치 공급자 비활성화 이벤트 처리
            }
        }

        // 위치 업데이트 요청
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 위치 권한이 없는 경우 권한 요청
            // 필요한 경우 권한 요청 로직을 추가해야 합니다.
            return
        }

        // 위치 관리자에 위치 업데이트 리스너 등록
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            500L,  // 위치 업데이트 간격 (1초)
            5f,     // 위치 업데이트 거리 (5미터)
            locationListener
        )
    }



    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        } else {
            // 위치 권한이 거부된 경우 처리
            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 사용자가 "다시 묻지 않음"을 체크한 경우, 설정 화면으로 안내하여 권한을 설정하도록 유도할 수 있습니다.
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.data = uri
                startActivity(intent)
            } else {
                // 사용자에게 위치 권한의 필요성을 안내하고 다시 요청할 수 있습니다.
                Toast.makeText(requireContext(), "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleLocationChange(location: Location) {
        // 위치 정보 업데이트 처리
        homelatitude = location.latitude
        homelongitude = location.longitude

        val myLatitude = homelatitude.toString()
        val myLongitude = homelongitude.toString()

        GlobalApplication.prefs.setString("myLatitude", myLatitude)
        GlobalApplication.prefs.setString("myLongitude", myLongitude)

        Log.d("Home : MyLocation1", "Latitude: $myLatitude, Longitude: $myLongitude")

        // 위치 정보
        Log.d("Home : MyLocation", "Latitude: $homelatitude, Longitude: $homelongitude")
        callRestaurantListAPI(category, homelatitude, homelongitude)
    }

    private fun onViewClicked(view: ConstraintLayout) {
        // 이전에 선택된 뷰의 색상을 원래대로 변경합니다.
        binding.all.setBackgroundResource(R.drawable.bg_home_item_radius)
        selectedView?.setBackgroundResource(R.drawable.bg_home_item_radius)

        // 선택된 뷰의 색상을 변경합니다.
        view.setBackgroundResource(R.drawable.bg_home_item_radius_click)

        // 선택된 뷰의 자식 View 중에서 TextView를 찾아서 색상을 변경합니다.
        for (i in 0 until view.childCount) {
            val child = view.getChildAt(i)
            if (child is TextView) {
                child.setTextColor(Color.parseColor("#000000"))
            }
        }

        // 선택되지 않은 뷰의 TextView 색상을 흰색으로 변경합니다.
        val views = listOf(
            binding.allText,
            binding.chineseText,
            binding.westernText,
            binding.asianText,
            binding.easternText,
            binding.bakeryText,
            binding.cafeText,
            binding.mexicanText,
            binding.saladText
        )
        views.filterNot { it.parent == view }.forEach {
            it.setTextColor(Color.parseColor("#FFFFFF"))
        }

        // 선택된 뷰를 저장합니다.
        selectedView = view
    }

    private fun callRestaurantListAPI(category: String, latitude: Double, longitude: Double){
        lifecycleScope.launch {
            try {
                val response = retService.homeCategory("Bearer $accessToken",category, latitude, longitude)
                if (response.isSuccessful) {
                    Log.d("Home", "Success, category: $category, latitude: $latitude, longitude: $longitude")
                    val restaurantListData = response.body()?.restaurantList
                    // Data processing
                    Log.d("Home", "restaurantListData.toString()")

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