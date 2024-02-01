package com.moondroid.pharmacyproject01.presentation.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.pharmacyproject01.R
import com.moondroid.pharmacyproject01.common.IntentParam
import com.moondroid.pharmacyproject01.common.afterTextChanged
import com.moondroid.pharmacyproject01.common.viewBinding
import com.moondroid.pharmacyproject01.databinding.ActivityAddressBinding
import com.moondroid.pharmacyproject01.presentation.base.BaseActivity

class AddressActivity : BaseActivity() {
    private val binding by viewBinding(ActivityAddressBinding::inflate)

    private val adapter = AddressAdapter {
        toList(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.icBack.setOnClickListener { finish() }
        binding.recycler.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        binding.recycler.adapter = adapter

        val locations = resources.getStringArray(R.array.location).toCollection(ArrayList())

        adapter.submitList(locations)

        binding.etAddress.afterTextChanged { edit ->
            val newLocation = locations.filter {
                it.contains(edit)
            }
            adapter.submitList(newLocation)
        }
    }

    private fun toList(address: String) {
        startActivity(Intent(mContext, ListActivity::class.java).apply {
            putExtra(IntentParam.ADDRESS, address)
        })
        finish()
    }
}