package com.example.quick_cheque.delegates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.InnerListMembersChequeAdapter
import com.example.quick_cheque.list_items.ChequeListItem
import com.example.quick_cheque.list_items.ListItem

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
        private val iconAdminInCheque: ImageView = itemView.findViewById(R.id.iconAdminInCheque)
        private val nameOwnerCheque: TextView = itemView.findViewById(R.id.nameOwnerCheque)
        private val sumOfCheque: TextView = itemView.findViewById(R.id.sumOfCheque)
        private val membersRecyclerViewList: RecyclerView =
            itemView.findViewById(R.id.listChequeMembers)
        private val constraintLayoutListItem: ConstraintLayout =
            itemView.findViewById(R.id.listItem)
        private val linearLayoutExpandableChequeInfo: LinearLayout =
            itemView.findViewById(R.id.fullInformationOfCheque)

        fun bind(expandableListItem: ChequeListItem) {
            if (expandableListItem.isExpanded) {
                constraintLayoutListItem.setBackgroundResource(R.drawable.style_cheque_card_expandable)
            } else {
                constraintLayoutListItem.setBackgroundResource(R.drawable.custom_layer_list)
            }

            if (!expandableListItem.isExpanded) { // Если не расширен, то схлопываем элемент
                linearLayoutExpandableChequeInfo.visibility = View.GONE
            }

            with(expandableListItem.cheque) {
                this@ExpandableListViewHolder.titleListItem.text = title
                this@ExpandableListViewHolder.nameOwnerCheque.text = owner.name
                this@ExpandableListViewHolder.iconAdminInCheque.setBackgroundResource(owner.icon)
                this@ExpandableListViewHolder.sumOfCheque.text = sumOfCheque.toString()

                this@ExpandableListViewHolder.membersRecyclerViewList.setHasFixedSize(true)
                this@ExpandableListViewHolder.membersRecyclerViewList.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                this@ExpandableListViewHolder.membersRecyclerViewList.adapter =
                    InnerListMembersChequeAdapter(membersCheque)
            }

            constraintLayoutListItem.setOnClickListener {
                linearLayoutExpandableChequeInfo.visibility =
                    if (expandableListItem.isExpanded) View.GONE else View.VISIBLE

                expandableListItem.isExpanded = !expandableListItem.isExpanded
                if (expandableListItem.isExpanded) {
                    constraintLayoutListItem.setBackgroundResource(R.drawable.style_cheque_card_expandable)
                } else {
                    constraintLayoutListItem.setBackgroundResource(R.drawable.custom_layer_list)
                }
            }
        }
    }
}