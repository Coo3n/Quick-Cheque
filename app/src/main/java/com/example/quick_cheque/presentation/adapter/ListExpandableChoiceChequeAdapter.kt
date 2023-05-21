package com.example.quick_cheque.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.CardChoiceItemBinding
import com.example.quick_cheque.domain.model.ChequeListItem
import com.example.quick_cheque.domain.model.User

class ListExpandableChoiceChequeAdapter(private val clickable: Clickable) :
    ListAdapter<ChequeListItem, ListExpandableChoiceChequeAdapter.ExpandableListViewHolder>(
        ListExpandableChoiceChequeDiffCallback()
    ) {

    private var lastClickedItem: ChequeListItem? = null
    private var lastClickedItemPosition: Int? = null

    interface Clickable {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableListViewHolder {
        return ExpandableListViewHolder(
            CardChoiceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    override fun onBindViewHolder(holder: ExpandableListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListExpandableChoiceChequeDiffCallback : DiffUtil.ItemCallback<ChequeListItem>() {
        override fun areItemsTheSame(oldItem: ChequeListItem, newItem: ChequeListItem): Boolean {
            return oldItem.cheque.id == newItem.cheque.id
        }

        override fun areContentsTheSame(oldItem: ChequeListItem, newItem: ChequeListItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class ExpandableListViewHolder(
        private val binding: CardChoiceItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var innerListMembersChequeAdapter: InnerListMembersChequeAdapter

        fun bind(chequeListItem: ChequeListItem) = with(binding) {
            changingStyleExpandableObjectInChequeListItem(chequeListItem)

            with(chequeListItem.cheque) {
                titleListItem.text = title
                nameOwnerCheque.text = owner.name
                iconAdminInCheque.setBackgroundResource(owner.icon)
                finalSumOfCheque.text = sumOfCheque.toString()
                setupMembersRecyclerList(membersCheque)
            }

            buttonAddNewMembersInCheque.setOnClickListener {
                innerListMembersChequeAdapter.addNewListMemberCheque(
                    User("Olya", "ds", R.drawable.person_filled)
                )
            }

            titleListItem.setOnClickListener {
                clickable.onClick(adapterPosition)
            }

            fullInformationOfCheque.setOnClickListener {
                clickable.onClick(adapterPosition)
            }

            expandableButton.setOnClickListener {
                chequeListItem.isExpanded = !chequeListItem.isExpanded
                changingStyleExpandableObjectInChequeListItem(chequeListItem)
            }
        }

        private fun changingStyleExpandableObjectInChequeListItem(chequeListItem: ChequeListItem) =
            with(binding) {
                previewListChoiceChequeItem.setBackgroundResource(
                    if (chequeListItem.isClicked) {
                        if (chequeListItem.isExpanded) {
                            fullInformationOfCheque.setBackgroundResource(R.drawable.style_bottom_highlighted_full_information)
                            R.drawable.style_highlighted_border_expandable_preview
                        } else {
                            R.drawable.style_highlighted_border_preview
                        }
                    } else {
                        if (chequeListItem.isExpanded) {
                            fullInformationOfCheque.setBackgroundResource(R.drawable.style_bottom_radius_for_cheque)
                            R.drawable.style_cheque_card_expandable
                        } else {
                            R.drawable.style_cheque_card_classic
                        }
                    }
                )

                expandableButton.rotation = if (chequeListItem.isExpanded) -90f else 90f

                fullInformationOfCheque.visibility = if (chequeListItem.isExpanded) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

        private fun setupMembersRecyclerList(listMembersCheque: MutableList<User>) = with(binding) {
            listChequeMembers.layoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL, false
            )
            innerListMembersChequeAdapter = InnerListMembersChequeAdapter()
            listChequeMembers.adapter = innerListMembersChequeAdapter
            innerListMembersChequeAdapter.submitList(listMembersCheque)
        }

    }
}

