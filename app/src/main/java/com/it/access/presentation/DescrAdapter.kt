package com.it.access.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.it.access.databinding.ItemDescriptionBinding


class DescrHolder private constructor(val ui: ItemDescriptionBinding): ViewHolder(ui.root) {
    fun bind(mItem: String) {
        ui.apply {
            item = mItem
            executePendingBindings()
        }
    }

    companion object {
        fun from(parent: ViewGroup) = DescrHolder(
            ItemDescriptionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class DescrAdapter: ListAdapter<String, DescrHolder>(DescrDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescrHolder {
        return DescrHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DescrHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DescrDiff: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
}