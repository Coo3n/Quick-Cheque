package com.example.quick_cheque.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.list_items.ListItem
import com.example.quick_cheque.list_items.ProductListItem

class ProductListDelegate : Delegate {
    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_payment, parent, false)
        )
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, listItem: ListItem) {
        (holder as ListViewHolder).bind(listItem as ProductListItem)
    }

    override fun forItem(listItem: ListItem): Boolean = listItem is ProductListItem

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val price: TextView = itemView.findViewById(R.id.price)
        private val count: TextView = itemView.findViewById(R.id.count)

        fun bind(listItem: ProductListItem) {

            with(listItem.product) {
                this@ListViewHolder.productName.text = name;
                this@ListViewHolder.price.text = price.toString() + "р/";
                this@ListViewHolder.count.text = count.toString() + "шт.";
            }

        }
    }
}
