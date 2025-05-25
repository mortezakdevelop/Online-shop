package com.example.onlineshopapplication.ui.base

import androidx.recyclerview.widget.DiffUtil
import com.example.onlineshopapplication.data.models.home.BannerResponse

class BaseDiffUtils<T> (private val oldItem:List<T>, private val newItem:List<T>):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
       return oldItem.size
    }

    override fun getNewListSize(): Int {
        return newItem.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition] === newItem[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItem[oldItemPosition] === newItem[newItemPosition]
    }
}