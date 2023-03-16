package com.example.quick_cheque.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.expandable_list_items.ChequeListItem
import com.example.quick_cheque.expandable_list_items.ListItem
import com.example.quick_cheque.model.User

class ExpandableListDelegate : Delegate {
    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ExpandableListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_cheque, parent, false)
        )
    }

    override fun bindViewHolder(holder: RecyclerView.ViewHolder, listItem: ListItem) {
        (holder as ExpandableListViewHolder).bind(listItem as ChequeListItem)
    }

    override fun forItem(listItem: ListItem): Boolean = listItem is ChequeListItem

    inner class ExpandableListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleListItem: TextView = itemView.findViewById(R.id.titleListItem)
        private val nameOwnerCheque: TextView = itemView.findViewById(R.id.nameOwnerCheque)
        private val sumOfCheque: TextView = itemView.findViewById(R.id.sumOfCheque)
        private val constraintLayoutListItem: ConstraintLayout =
            itemView.findViewById(R.id.listItem)
        private val linearLayoutExpandableChequeInfo: LinearLayout =
            itemView.findViewById(R.id.fullInformationOfCheque)

        fun bind(expandableListItem: ChequeListItem) {
            with(expandableListItem.cheque) {
                owner = User(nameOwnerCheque.toString())
                title = titleListItem.toString()
                sumOfCheque = sumOfCheque.toString().toInt()
            }

            constraintLayoutListItem.setOnClickListener {
                expandableListItem.isExpanded = !expandableListItem.isExpanded
                linearLayoutExpandableChequeInfo.visibility =
                    if (expandableListItem.isExpanded) View.VISIBLE else View.GONE
            }
        }
    }
}