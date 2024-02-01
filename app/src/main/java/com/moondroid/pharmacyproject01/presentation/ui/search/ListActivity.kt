package com.moondroid.pharmacyproject01.presentation.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.pharmacyproject01.common.IntentParam
import com.moondroid.pharmacyproject01.common.collectEvent
import com.moondroid.pharmacyproject01.common.viewBinding
import com.moondroid.pharmacyproject01.databinding.ActivityListBinding
import com.moondroid.pharmacyproject01.presentation.base.BaseActivity
import com.moondroid.pharmacyproject01.presentation.base.viewModel
import com.moondroid.pharmacyproject01.presentation.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : BaseActivity() {
    private val binding by viewBinding(ActivityListBinding::inflate)
    private val viewModel by viewModel<ListViewModel>()

    private val adapter = PagingAdapter({
        showLoading(false)
    }) {
        toDetail(it)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val address = intent.getStringExtra(IntentParam.ADDRESS) ?: return
        val params = address.split(" ")

        binding.tvTitle.text = params[1] + " 약국 목록"
        binding.icBack.setOnClickListener { finish() }
        showLoading(true)

        collectEvent(viewModel.eventFlow, ::handleEvent)

        binding.recycler.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        binding.recycler.adapter = adapter

        viewModel.getListByAddress(params[0], params[1])
    }

    private fun handleEvent(event: ListViewModel.Event) {
        when (event) {
            is ListViewModel.Event.Update -> {
                lifecycleScope.launch {
                    adapter.submitData(event.data)
                }
            }
        }
    }

    private fun toDetail(hpid: String){
        startActivity(Intent(mContext, DetailActivity::class.java).apply {
            putExtra(IntentParam.HPID, hpid)
        })
    }

}