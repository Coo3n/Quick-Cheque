package com.example.quick_cheque.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.CardChoiceItemBinding
import com.example.quick_cheque.model.ChequeListItem
import com.example.quick_cheque.model.User

class ListExpandableChoiceChequeAdapter(private val clickable: Clickable) :
    ListAdapter<ChequeListItem, ListExpandableChoiceChequeAdapter.ExpandableListViewHolder>(
        ListExpandableChoiceChequeDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableListViewHolder {
        return ExpandableListViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExpandableListViewHolder, position: Int) {
        holder.bind(getItem(position), clickable)
    }

    class ListExpandableChoiceChequeDiffCallback : DiffUtil.ItemCallback<ChequeListItem>() {
        override fun areItemsTheSame(oldItem: ChequeListItem, newItem: ChequeListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ChequeListItem, newItem: ChequeListItem): Boolean {
            return oldItem.cheque == newItem.cheque && oldItem.isExpanded == newItem.isExpanded
        }
    }

    interface Clickable {
        fun onClick(position: Int)
    }

    class ExpandableListViewHolder(
        private val binding: CardChoiceItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var innerListMembersChequeAdapter: InnerListMembersChequeAdapter

        private var cnt = 0;

        companion object {
            fun create(parent: ViewGroup): ExpandableListViewHolder {
                return ExpandableListViewHolder(
                    CardChoiceItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        fun bind(expandableListItem: ChequeListItem, clickable: Clickable) = with(binding) {
            changingStyleExpandableObjectInChequeListItem(expandableListItem.isExpanded)

            with(expandableListItem.cheque) {
                titleListItem.text = title
                nameOwnerCheque.text = owner.name
                iconAdminInCheque.setBackgroundResource(owner.icon)
                finalSumOfCheque.text = sumOfCheque.toString()

                setupMembersRecyclerList(membersCheque)
            }

            buttonAddNewMembersInCheque.setOnClickListener {
                innerListMembersChequeAdapter.addNewListMemberCheque(
                    if (cnt % 2 == 0) {
                        User("Olua", R.drawable.person_filled)
                    } else {
                        User("Olua", R.drawable.payment)
                    }
                )

                cnt++
            }

            titleListItem.setOnClickListener {
                clickable.onClick(adapterPosition)
            }

            expandableButton.setOnClickListener {
                expandableListItem.isExpanded = !expandableListItem.isExpanded
                changingStyleExpandableObjectInChequeListItem(expandableListItem.isExpanded)
            }
        }


        private fun changingStyleExpandableObjectInChequeListItem(isExpandedListItem: Boolean) {
            with(binding) {
                if (isExpandedListItem) {
                    previewListChoiceChequeItem.setBackgroundResource(R.drawable.style_cheque_card_expandable)
                    expandableButton.rotation = 90f
                    fullInformationOfCheque.visibility = View.VISIBLE
                } else {
                    previewListChoiceChequeItem.setBackgroundResource(R.drawable.style_cheque_card_classic)
                    expandableButton.rotation = 0f
                    fullInformationOfCheque.visibility = View.GONE
                }
            }
        }

        private fun setupMembersRecyclerList(listMembersCheque: MutableList<User>) = with(binding) {
            listChequeMembers.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            innerListMembersChequeAdapter = InnerListMembersChequeAdapter()
            listChequeMembers.adapter = innerListMembersChequeAdapter
            innerListMembersChequeAdapter.submitList(listMembersCheque)
        }
    }
}

