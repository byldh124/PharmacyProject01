package com.moondroid.pharmacyproject01.presentation.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.moondroid.pharmacyproject01.BuildConfig
import com.moondroid.pharmacyproject01.common.exitApp
import com.moondroid.pharmacyproject01.common.logException
import com.moondroid.pharmacyproject01.common.viewBinding
import com.moondroid.pharmacyproject01.databinding.ActivitySplashBinding
import com.moondroid.pharmacyproject01.domain.Repository
import com.moondroid.pharmacyproject01.presentation.base.BaseActivity
import com.moondroid.pharmacyproject01.presentation.dialog.ButtonDialog
import com.moondroid.pharmacyproject01.presentation.ui.home.HomeActivity
import com.moondroid.pharmacyproject01.presentation.ui.search.AddressActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private val binding by viewBinding(ActivitySplashBinding::inflate)
    private val viewModel: SplashViewModel by viewModels()

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnMap.setOnClickListener {
            requestPermission()
        }

        binding.btnSearch.setOnClickListener {
            startActivity(Intent(mContext, AddressActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        checkAppVersion()
    }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            val deniedList: List<String> = result.filter {
                !it.value
            }.map {
                it.key
            }

            if (deniedList.isNotEmpty()) {
                ButtonDialog.Builder(mContext).apply {
                    message = "앱 설정에서 위치 권한 설정 후 사용이 가능합니다."
                    setPositiveButton("확인") {
                        val settingIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        settingIntent.setData(Uri.parse("package:$packageName"))
                        startActivity(settingIntent)
                    }
                }.show()
            } else {
                checkGps()
            }
        }

    private fun requestPermission() {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun checkGps() {
        val locationManager = mContext.getSystemService(LocationManager::class.java)
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            startActivity(Intent(mContext, HomeActivity::class.java))
        } else {
            ButtonDialog.Builder(mContext).apply {
                message = "앱 사용을 위해 위치 정보를 설정해주세요."
                setPositiveButton("확인") {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            }.show()
        }
    }

    private fun checkAppVersion() {
        CoroutineScope(Dispatchers.Main).launch {
            repository.checkAppVersion(
                BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME, packageName
            ).collect {
                when (it) {
                    2003 -> requestVersionUpdate()
                }
            }
        }
    }

    /**
     *  플레이스토어로 이동
     */
    private fun requestVersionUpdate() {
        val builder = ButtonDialog.Builder(mContext).apply {
            message = "새로운 버전이 출시됐습니다.\\n업데이트 후 이용이 가능합니다."
            setPositiveButton("업데이트") {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("market://details?id=$packageName")
                    startActivity(intent)
                } catch (e: Exception) {
                    logException(e)
                    exitApp()
                }
            }
            setNegativeButton("나가기", ::finish)
            cancelable = false
        }
        builder.show()
    }
}