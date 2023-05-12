package com.greenranger.seoulforveggi.view

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
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
//    private lateinit var retService: APIS
    // 선택된 ConstraintLayout을 저장할 변수
    private var selectedView: ConstraintLayout? = null
    //위치 정보 관리자
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 123

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        //retrofit
//        retService = RetrofitClient
//            .getRetrofitInstance()
//            .create(APIS::class.java)

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


        //all 카테고리 선택으로 초기화
        binding.all.setBackgroundResource(R.drawable.bg_home_item_radius_click)
        binding.allText.setTextColor(Color.parseColor("#000000"))

//      각 ConstraintLayout에 클릭 리스너를 등록합니다.
        binding.all.setOnClickListener {
            onViewClicked(binding.all)
            binding.categories.text = "All"
            //서버에 요청
             }
        binding.chinese.setOnClickListener {
            onViewClicked(binding.chinese)
            binding.categories.text = "Chinese Food"
            //서버에 요청
        }
        binding.western.setOnClickListener {
            onViewClicked(binding.western)
            binding.categories.text = "Western Food"
            //서버에 요청
        }
        binding.asian.setOnClickListener {
            onViewClicked(binding.asian)
            binding.categories.text = "Western Food"
            //서버에 요청
        }
        binding.eastern.setOnClickListener {
            onViewClicked(binding.eastern)
            binding.categories.text = "Middle Eastern Food"
            //서버에 요청
        }
        binding.bakery.setOnClickListener {
            onViewClicked(binding.bakery)
            binding.categories.text = "Bakery"
            //서버에 요청
        }
        binding.cafe.setOnClickListener {
            onViewClicked(binding.cafe)
            binding.categories.text = "Cafe"
            //서버에 요청
        }
        binding.mexican.setOnClickListener {
            onViewClicked(binding.mexican)
            binding.categories.text = "Mexican Food"
            //서버에 요청
        }
        binding.salad.setOnClickListener {
            onViewClicked(binding.salad)
            binding.categories.text = "Salad"
            //서버에 요청
        }


        return binding.root
    }

    private fun startLocationUpdates() {
        // 위치 업데이트 리스너 등록
        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                // 위치 정보 업데이트 처리
                val latitude = location.latitude
                val longitude = location.longitude

                // 위치 정보
                Log.d("Home : MyLocation", "Latitude: $latitude, Longitude: $longitude")

                // 서버에 위치 정보를 전송.


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
            5000L,  // 위치 업데이트 간격 (5초)
            10f,    // 위치 업데이트 거리 (10미터)
            locationListener
        )
    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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

}