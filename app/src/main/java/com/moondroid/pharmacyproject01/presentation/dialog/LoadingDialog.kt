package com.moondroid.pharmacyproject01.presentation.dialog

import android.content.Context
import android.os.Bundle
import com.moondroid.pharmacyproject01.R
import com.moondroid.pharmacyproject01.common.debug
import com.moondroid.pharmacyproject01.common.glide
import com.moondroid.pharmacyproject01.databinding.DialogLoadingBinding
import com.moondroid.pharmacyproject01.presentation.base.BaseDialog

class LoadingDialog(context: Context) : BaseDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debug("LoadingDialog - onCreate()")
        val binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ivLoading.glide(R.drawable.loading)
    }

    override fun show() {
        super.show()
        debug("LoadingDialog - show()")
    }

    override fun cancel() {
        super.cancel()
        debug("LoadingDialog - cancel()")

    }
}