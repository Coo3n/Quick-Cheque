package com.example.quick_cheque.diff_utils

import androidx.recyclerview.widget.DiffUtil
import com.example.quick_cheque.list_items.ListItem


class DiffUtilForListDelegates(
    private val oldListItem: List<ListItem>,
    private val newListItem: List<ListItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldListItem.size

    override fun getNewListSize(): Int = newListItem.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListItem[oldItemPosition] == newListItem[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListItem[oldItemPosition] == newListItem[newItemPosition]
    }

}