package com.moondroid.pharmacyproject01.presentation.ui.detail

import android.os.Bundle
import com.moondroid.pharmacyproject01.common.IntentParam
import com.moondroid.pharmacyproject01.common.viewBinding
import com.moondroid.pharmacyproject01.databinding.ActivityDetailBinding
import com.moondroid.pharmacyproject01.presentation.base.BaseActivity
import com.moondroid.pharmacyproject01.presentation.base.viewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity() {
    private val binding by viewBinding(ActivityDetailBinding::inflate)
    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.model = viewModel
        binding.icBack.setOnClickListener { finish() }

        val hpid: String = intent.getStringExtra(IntentParam.HPID)!!
        viewModel.getDetail(hpid)
    }
}