package com.example.quick_cheque.delegates

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
import com.example.quick_cheque.model.User

class ExpandableListDelegate : Delegate {
    override fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ExpandableListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_choice_item, parent, false)
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
        private val finalSumOfCheque: TextView = itemView.findViewById(R.id.finalSumOfCheque)

        private val membersRecyclerViewList: RecyclerView =
            itemView.findViewById(R.id.listChequeMembers)

        private val buttonAddNewMembersInCheque: ImageButton =
            itemView.findViewById(R.id.buttonAddNewMembersInCheque)

        private val expandableButton: ImageView = itemView.findViewById(R.id.expandableButton)

        private val previewListItem: ConstraintLayout =
            itemView.findViewById(R.id.listItem)

        private val expandableInfoOfCheque: LinearLayout =
            itemView.findViewById(R.id.fullInformationOfCheque)
        private var cnt = 0;

        fun bind(expandableListItem: ChequeListItem) {
            changingStyleExpandableObjectInChequeListItem(expandableListItem.isExpanded)

            with(expandableListItem.cheque) {
                titleListItem.text = title
                nameOwnerCheque.text = owner.name
                iconAdminInCheque.setBackgroundResource(owner.icon)
                finalSumOfCheque.text = sumOfCheque.toString()

                setupMembersRecyclerViewList(membersCheque)
            }

            buttonAddNewMembersInCheque.setOnClickListener {
                (membersRecyclerViewList.adapter as InnerListMembersChequeAdapter).addMemberInCheque(
                    if (cnt % 2 == 0) {
                        User("Olua", R.drawable.person_filled)
                    } else {
                        User("Olua", R.drawable.payment)

                    }
                )
                cnt++
            }

            expandableButton.setOnClickListener {
                expandableListItem.isExpanded = !expandableListItem.isExpanded
                changingStyleExpandableObjectInChequeListItem(expandableListItem.isExpanded)
            }
        }

        private fun changingStyleExpandableObjectInChequeListItem(isExpandedListItem: Boolean) {
            if (isExpandedListItem) {
                previewListItem.setBackgroundResource(R.drawable.style_cheque_card_expandable)
                expandableButton.rotation = 90f
                expandableInfoOfCheque.visibility = View.VISIBLE
            } else {
                previewListItem.setBackgroundResource(R.drawable.style_cheque_card_classic)
                expandableButton.rotation = 0f
                expandableInfoOfCheque.visibility = View.GONE
            }
        }

        private fun setupMembersRecyclerViewList(membersCheque: MutableList<User>) {
            membersRecyclerViewList.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            membersRecyclerViewList.adapter = InnerListMembersChequeAdapter(membersCheque)
        }
    }
}