package com.moondroid.pharmacyproject01.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.moondroid.pharmacyproject01.common.debug
import com.moondroid.pharmacyproject01.common.viewBinding
import com.moondroid.pharmacyproject01.databinding.ActivitySplashBinding
import com.moondroid.pharmacyproject01.presentation.base.BaseActivity
import com.moondroid.pharmacyproject01.presentation.ui.home.HomeActivity
import com.moondroid.pharmacyproject01.presentation.ui.search.AddressActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private val binding by viewBinding(ActivitySplashBinding::inflate)
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnMap.setOnClickListener {
            startActivity(Intent(mContext, HomeActivity::class.java))
        }

        binding.btnSearch.setOnClickListener {
            startActivity(Intent(mContext, AddressActivity::class.java))
        }
    }
}