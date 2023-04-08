package com.example.quick_cheque.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
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

    var previousClickedChequeListItem: PreviousClickedChequeListItem? = null

    data class PreviousClickedChequeListItem(
        var clickedChequeListItem: ChequeListItem? = null,
        var clickedPreviewElement: ConstraintLayout? = null,
        var clickedFullInformationElement: LinearLayout? = null
    )

    interface Clickable {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableListViewHolder {
        return ExpandableListViewHolder(
            CardChoiceItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExpandableListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListExpandableChoiceChequeDiffCallback : DiffUtil.ItemCallback<ChequeListItem>() {
        override fun areItemsTheSame(oldItem: ChequeListItem, newItem: ChequeListItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ChequeListItem, newItem: ChequeListItem): Boolean {
            return oldItem.cheque == newItem.cheque && oldItem.isExpanded == newItem.isExpanded
        }
    }

    inner class ExpandableListViewHolder(
        private val binding: CardChoiceItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var innerListMembersChequeAdapter: InnerListMembersChequeAdapter

        fun bind(expandableListItem: ChequeListItem) = with(binding) {
            changingStyleExpandableObjectInChequeListItem(
                expandableListItem.isExpanded,
                expandableListItem.isClicked
            )

            Log.i(
                "MyTag",
                adapterPosition.toString() + "expanded:" + expandableListItem.isExpanded + " clicked: " + expandableListItem.isClicked
            )

            with(expandableListItem.cheque) {
                titleListItem.text = title
                nameOwnerCheque.text = owner.name
                iconAdminInCheque.setBackgroundResource(owner.icon)
                finalSumOfCheque.text = sumOfCheque.toString()
                setupMembersRecyclerList(membersCheque)
            }

            buttonAddNewMembersInCheque.setOnClickListener {
                innerListMembersChequeAdapter.addNewListMemberCheque(
                    User("Olya", R.drawable.person_filled)
                )
            }

            titleListItem.setOnClickListener {
                handleChequeListItemClick(
                    expandableListItem,
                    previewListChoiceChequeItem,
                    fullInformationOfCheque
                )
            }

            fullInformationOfCheque.setOnClickListener {
                handleChequeListItemClick(
                    expandableListItem,
                    previewListChoiceChequeItem,
                    fullInformationOfCheque
                )
            }

            expandableButton.setOnClickListener {
                expandableListItem.isExpanded = !expandableListItem.isExpanded
                changingStyleExpandableObjectInChequeListItem(
                    expandableListItem.isExpanded,
                    expandableListItem.isClicked
                )
            }
        }

        private fun handleChequeListItemClick(
            expandableListItem: ChequeListItem,
            previewListChoiceChequeItem: ConstraintLayout?,
            fullInformationOfCheque: LinearLayout?
        ) {
            // Если еще не кликнули на какой-нибудь элемент пройдет мимо
            if (previousClickedChequeListItem != null) {
                previousClickedChequeListItem?.clickedChequeListItem?.isClicked = false
                // Меняем стиль у предыдущего элемента на стандартный
                changeStyleLastClickedListElement(previousClickedChequeListItem)
            }

            // записываем последний кликнутый элемент
            previousClickedChequeListItem = PreviousClickedChequeListItem(
                expandableListItem,
                previewListChoiceChequeItem,
                fullInformationOfCheque
            )
            expandableListItem.isClicked = true

            changingStyleExpandableObjectInChequeListItem( // меняем стиль у кликнутого элемента
                expandableListItem.isExpanded,
                expandableListItem.isClicked
            )

            clickable.onClick(adapterPosition) // отдаем callback во фрагмент с позицией клика
        }

        private fun changingStyleExpandableObjectInChequeListItem(
            isExpandedListItem: Boolean,
            isClicked: Boolean
        ) = with(binding) {
            previewListChoiceChequeItem.setBackgroundResource(
                if (isClicked) {
                    if (isExpandedListItem) {
                        fullInformationOfCheque.setBackgroundResource(R.drawable.style_bottom_highlighted_full_information)
                        R.drawable.style_highlighted_border_expandable_preview
                    } else {
                        R.drawable.style_highlighted_border_preview
                    }
                } else {
                    if (isExpandedListItem) {
                        fullInformationOfCheque.setBackgroundResource(R.drawable.style_bottom_radius_for_cheque)
                        R.drawable.style_cheque_card_expandable
                    } else {
                        R.drawable.style_cheque_card_classic
                    }
                }
            )

            expandableButton.rotation = if (isExpandedListItem) -90f else 90f
            fullInformationOfCheque.visibility = if (isExpandedListItem) View.VISIBLE else View.GONE
        }


        private fun changeStyleLastClickedListElement(
            previousClickedChequeListItem: PreviousClickedChequeListItem?
        ) = with(previousClickedChequeListItem) {
            this?.clickedPreviewElement?.setBackgroundResource(
                if (clickedChequeListItem?.isExpanded == true) {
                    R.drawable.style_cheque_card_expandable
                } else {
                    R.drawable.style_cheque_card_classic
                }
            )

            this?.clickedFullInformationElement?.setBackgroundResource(R.drawable.style_bottom_radius_for_cheque)
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

