package com.moondroid.pharmacyproject01.presentation.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.moondroid.pharmacyproject01.common.debug
import com.moondroid.pharmacyproject01.databinding.ItemAddressBinding

class AddressAdapter(private val onClick: (String) -> Unit) : ListAdapter<String, AddressAdapter.VH>(StringDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemAddressBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val address = getItem(position)
        holder.bind(address)
    }

    inner class VH(
        private val binding: ItemAddressBinding,
    ) : ViewHolder(binding.root) {
        fun bind(address: String) {
            binding.address = address
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onClick(address)
            }
        }
    }

    object StringDiff : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
    }
}