package com.greenranger.seoulforveggi.view.fragment

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.FragmentNaverMapBinding
import com.greenranger.seoulforveggi.view.base.BaseFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource

class NaverMapFragment : BaseFragment<FragmentNaverMapBinding>(), OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val permission_request = 99

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNaverMapBinding {
        return FragmentNaverMapBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check permissions
        if (isPermitted()) {
            startProcess()
        } else {
            requestPermissions(permissions, permission_request)
        }

        NaverMapSdk.getInstance(requireContext()).client =
            NaverMapSdk.NaverCloudPlatformClient("2s7rl7li7r")

    }

    // Check if permissions are granted
    fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    perm
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    // Load the map if permissions are granted
    fun startProcess() {
        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.mapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapView, it).commit()
            }
        mapFragment.getMapAsync(this)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permission_request) {
            if (isPermitted()) {
//                startProcess()
            } else {
                requireActivity().finish()
            }
        }
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {

        val cameraPosition = CameraPosition(
            LatLng(37.5666102, 126.9783881),  // 위치 지정
            16.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition
        this.naverMap = naverMap

        //zoom 제한
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setUpdateLocationListener()
    }

    // 생명주기
    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding.mapView.onDestroy()
//    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }



    @SuppressLint("MissingPermission")
    fun setUpdateLocationListener() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY //정확도 높게
            interval = 10000 //10초에 한번씩 GPS 요청
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                if (result == null) return
                for (location in result.locations) {
                    Log.d("location: ", "${location.latitude}, ${location.longitude}")
                    setLastLocation(location)
                    //현재위치 파란점으로 나타내기
                    naverMap.locationOverlay.run {
                        isVisible = true
                        position = LatLng(location!!.latitude, location.longitude)
                    }
                }
            }
        }

        //좌표계를 주기적으로 갱신
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    fun setLastLocation(location: Location) {
        val myLocation = LatLng(location.latitude, location.longitude)
        val marker = Marker()
        marker.position = myLocation

//        marker.map = naverMap
        //마커
        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0

        marker.map = null
    }
}
   


