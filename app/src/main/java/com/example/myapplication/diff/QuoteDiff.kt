package com.example.myapplication.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.practice.models.Quotes

class QuoteDiff(
    private val oldList: List<Quotes>,
    private val newList: List<Quotes>
):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition].quote == newList[newItemPosition].quote
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}