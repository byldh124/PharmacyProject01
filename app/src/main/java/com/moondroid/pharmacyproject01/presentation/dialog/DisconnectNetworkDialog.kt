package com.moondroid.pharmacyproject01.presentation.dialog

import android.content.Context
import android.os.Bundle
import com.moondroid.pharmacyproject01.databinding.DialogDisconnectNetworkBinding
import com.moondroid.pharmacyproject01.presentation.base.BaseDialog

class DisconnectNetworkDialog(context: Context) : BaseDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogDisconnectNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}