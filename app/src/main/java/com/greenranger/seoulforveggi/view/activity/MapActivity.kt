package com.greenranger.seoulforveggi.view.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.databinding.ActivityMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource


class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityMapBinding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
    val permission_request = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check permissions
        if (isPermitted()) {
            startProcess()
        } else {
            ActivityCompat.requestPermissions(this, permissions, permission_request)
        }

        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient("2s7rl7li7r")
    }

    // Check if permissions are granted
    fun isPermitted(): Boolean {
        for (perm in permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    // Load the map if permissions are granted
    fun startProcess() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.mapView) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapView, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    // Handle permission request result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permission_request) {
            if (isPermitted()) {
                startProcess()
            } else {
                finish()
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        setUpdateLocationListner() //내위치를 가져오는 코드
    }

    //내 위치를 가져오는 코드
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient //자동으로 gps값을 받아온다.
    lateinit var locationCallback: LocationCallback //gps응답 값을 가져온다.
    //lateinit: 나중에 초기화 해주겠다는 의미

    @SuppressLint("MissingPermission")
    fun setUpdateLocationListner() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY //높은 정확도
            interval = 10000 //10초에 한번씩 GPS 요청
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                if (result == null) return
                for (location in result.locations) {
                    Log.d("location: ", "${location.latitude}, ${location.longitude}")
                    setLastLocation(location)
                    //현재위치 파란점으로 나타내기
                    naverMap.locationOverlay.run {
                        isVisible = true
                        position = LatLng(location!!.latitude, location!!.longitude)
                    }
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }//좌표계를 주기적으로 갱신

    fun setLastLocation(location: Location) {
        val myLocation = LatLng(location.latitude, location.longitude)
        val marker = Marker()
        marker.position = myLocation

        marker.map = naverMap
        //마커
        val cameraUpdate = CameraUpdate.scrollTo(myLocation)
        naverMap.moveCamera(cameraUpdate)
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 5.0

        //marker.map = null
    }

}
