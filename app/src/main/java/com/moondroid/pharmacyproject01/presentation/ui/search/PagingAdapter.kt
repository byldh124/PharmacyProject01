package com.moondroid.pharmacyproject01.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moondroid.pharmacyproject01.data.model.AddressItem
import com.moondroid.pharmacyproject01.databinding.ItemPharmacyBinding

class PagingAdapter(
    private val callback: () -> Unit,
    private val onClick: (String) -> Unit,
) : PagingDataAdapter<AddressItem, PagingAdapter.VH>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemPharmacyBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        callback()
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    inner class VH(
        private val binding: ItemPharmacyBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AddressItem) {
            binding.pharmacy = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onClick(item.hpid)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<AddressItem>() {
            override fun areItemsTheSame(oldItem: AddressItem, newItem: AddressItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: AddressItem, newItem: AddressItem): Boolean =
                oldItem.hpid == newItem.hpid
        }
    }
}



