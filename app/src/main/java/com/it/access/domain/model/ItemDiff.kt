package com.it.access.domain.model

import androidx.recyclerview.widget.DiffUtil
import com.it.access.data.response.ItemResp

class ItemDiff: DiffUtil.ItemCallback<ItemResp>() {
    override fun areItemsTheSame(oldItem: ItemResp, newItem: ItemResp) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ItemResp, newItem: ItemResp) =
        oldItem == newItem
}