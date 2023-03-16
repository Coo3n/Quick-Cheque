package com.example.quick_cheque.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.delegates.Delegate
import com.example.quick_cheque.expandable_list_items.ListItem

class ListAdapterWithDelegates(
    var delegates: List<Delegate>,
    var listItems: List<ListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].getViewHolder(parent)

    override fun getItemViewType(position: Int): Int =
        delegates.indexOfFirst { delegate -> delegate.forItem(listItems[position]) }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegates[getItemViewType(position)].bindViewHolder(holder, listItems[position])
    }

    override fun getItemCount(): Int = listItems.size
}