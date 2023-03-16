package com.example.quick_cheque.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.expandable_list_items.ListItem

interface Delegate {
    fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun bindViewHolder(holder: RecyclerView.ViewHolder, listItem: ListItem)
    fun forItem(listItem: ListItem): Boolean
}