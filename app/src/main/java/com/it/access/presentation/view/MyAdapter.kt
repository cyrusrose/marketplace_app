package com.it.access.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.it.access.data.response.ItemResp
import com.it.access.databinding.ItemBinding

class ItemDiff: DiffUtil.ItemCallback<ItemResp>() {
    override fun areItemsTheSame(oldItem: ItemResp, newItem: ItemResp) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ItemResp, newItem: ItemResp) =
        oldItem == newItem
}

fun interface NotifyListener{
    fun notify(item: ItemResp)
}

class MyViewHolder private constructor(val ui: ItemBinding, val ls: NotifyListener?): ViewHolder(ui.root) {
    fun bind(mItem: ItemResp) {
        ui.apply {
            item = mItem
            executePendingBindings()

            root.setOnClickListener {
                ls?.notify(mItem)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup, ls: NotifyListener?) = MyViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            ls
        )
    }
}

class MyAdapter: ListAdapter<ItemResp, MyViewHolder>(ItemDiff()) {
    var notifyListener: NotifyListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent, notifyListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}