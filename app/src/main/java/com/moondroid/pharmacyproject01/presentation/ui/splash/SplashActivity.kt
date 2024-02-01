package com.moondroid.pharmacyproject01.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
            startActivity(Intent(mContext, HomeActivity::class.java))
        }

        binding.btnSearch.setOnClickListener {
            startActivity(Intent(mContext, AddressActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        checkAppVersion()
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