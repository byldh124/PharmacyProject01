package com.moondroid.pharmacyproject01.presentation.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.google.android.gms.location.LocationServices
import com.moondroid.pharmacyproject01.R
import com.moondroid.pharmacyproject01.common.IntentParam
import com.moondroid.pharmacyproject01.common.collectEvent
import com.moondroid.pharmacyproject01.common.dpToPixel
import com.moondroid.pharmacyproject01.common.viewBinding
import com.moondroid.pharmacyproject01.databinding.ActivityHomeBinding
import com.moondroid.pharmacyproject01.presentation.base.BaseActivity
import com.moondroid.pharmacyproject01.presentation.base.viewModel
import com.moondroid.pharmacyproject01.presentation.ui.detail.DetailActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity(), OnMapReadyCallback {
    private val binding by viewBinding(ActivityHomeBinding::inflate)
    private val viewModel by viewModel<HomeViewModel>()

    private val requestCode = 0xf0f0
    private lateinit var locationSource: FusedLocationSource
    private lateinit var mNaverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.icBack.setOnClickListener { finish() }

        initView(savedInstanceState)
        collectEvent(viewModel.eventFlow, ::handleEvent)
    }

    private fun handleEvent(event: HomeViewModel.Event) {
        when (event) {
            // 모임정보 불러오 이후 마커 표시
            is HomeViewModel.Event.Update -> {
                event.list.forEach { item ->
                    val marker = Marker()
                    marker.apply {
                        position = LatLng(item.latitude, item.longitude)
                        map = mNaverMap
                        width = 28f.dpToPixel(mContext)
                        height = 28f.dpToPixel(mContext)
                        captionText = item.dutyName
                        tag = item
                        marker.icon = OverlayImage.fromResource(R.drawable.img_pharmacy)
                        onClickListener = Overlay.OnClickListener {
                            toDetail(item.hpid)
                            true
                        }
                    }
                }
            }
        }
    }

    private fun toDetail(hpid: String) {
        startActivity(Intent(mContext, DetailActivity::class.java).apply {
            putExtra(IntentParam.HPID, hpid)
        })
    }

    private fun initView(savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, requestCode)
    }

    private var reason = 0

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: NaverMap) {
        mNaverMap = map

        //보여지는 화면 설정
        map.mapType = NaverMap.MapType.Basic

        map.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
        map.isIndoorEnabled = true

        map.addOnCameraChangeListener { reason, _ ->
            this.reason = reason
        }

        // 카메라의 움직임 종료에 대한 이벤트 리스너 인터페이스.
        map.addOnCameraIdleListener {
            if (reason == -1) {
                val position = map.cameraPosition.target
                val zoom = map.cameraPosition.zoom
                if (zoom >= 14.0) {
                    val numOfRows = (19 - zoom.toInt()) * 20
                    viewModel.get(numOfRows, position.longitude, position.latitude)
                }
            }
        }

        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { success: Location? ->
                success?.let { location ->
                    showLoading(true)
                    val lng = location.longitude
                    val lat = location.latitude
                    val cameraPosition =
                        CameraPosition(LatLng(lat, lng), 16.0, 0.0, 0.0)
                    map.cameraPosition = cameraPosition
                    viewModel.get(30, lng, lat)
                }
            }
        map.maxZoom = 18.0

        mNaverMap.locationSource = locationSource
        val uiSettings = mNaverMap.uiSettings
        mNaverMap.locationTrackingMode = LocationTrackingMode.Follow

        uiSettings.apply {
            isCompassEnabled = true
            isLocationButtonEnabled = true
            isLogoClickEnabled = true
            isScrollGesturesEnabled = true
            isZoomControlEnabled = true
            isRotateGesturesEnabled = true
        }
    }

    //맵뷰 사용시 액티비티의 생명주기에 따른 맵뷰 생명주기 설정.
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
}