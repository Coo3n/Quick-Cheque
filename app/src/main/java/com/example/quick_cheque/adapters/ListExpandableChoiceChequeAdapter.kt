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
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.User

class ListExpandableChoiceChequeAdapter(private val clickable: Clickable) :
    ListAdapter<Cheque, ListExpandableChoiceChequeAdapter.ExpandableListViewHolder>(
        ListExpandableChoiceChequeDiffCallback()
    ) {

    var previousClickedCheque: ExpandableListViewHolder? = null

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

    class ListExpandableChoiceChequeDiffCallback : DiffUtil.ItemCallback<Cheque>() {
        override fun areItemsTheSame(oldItem: Cheque, newItem: Cheque): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cheque, newItem: Cheque): Boolean {
            return oldItem == newItem
        }
    }

    inner class ExpandableListViewHolder(
        private val binding: CardChoiceItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var innerListMembersChequeAdapter: InnerListMembersChequeAdapter
        private var isClicked: Boolean = false
        private var isExpanded: Boolean = false

        fun bind(cheque: Cheque) = with(binding) {
            changingStyleExpandableObjectInChequeListItem()

            with(cheque) {
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
                handleChequeListItemClick()
            }

            fullInformationOfCheque.setOnClickListener {
                handleChequeListItemClick()
            }

            expandableButton.setOnClickListener {
                isExpanded = !isExpanded
                changingStyleExpandableObjectInChequeListItem()
            }
        }

        private fun handleChequeListItemClick() {
            // Если еще не кликнули на какой-нибудь элемент пройдет мимо
            if (previousClickedCheque != null) {
                previousClickedCheque?.isClicked = false
                // Меняем стиль у предыдущего элемента на стандартный
                previousClickedCheque?.changeStyleLastClickedListElement()
            }

            //записываем последний кликнутый элемент
            previousClickedCheque = this

            isClicked = true

            changingStyleExpandableObjectInChequeListItem()// меняем стиль у кликнутого элемента

            clickable.onClick(adapterPosition) // отдаем callback во фрагмент с позицией клика
        }

        private fun changingStyleExpandableObjectInChequeListItem() = with(binding) {
            previewListChoiceChequeItem.setBackgroundResource(
                if (isClicked) {
                    if (isExpanded) {
                        fullInformationOfCheque.setBackgroundResource(R.drawable.style_bottom_highlighted_full_information)
                        R.drawable.style_highlighted_border_expandable_preview
                    } else {
                        R.drawable.style_highlighted_border_preview
                    }
                } else {
                    if (isExpanded) {
                        fullInformationOfCheque.setBackgroundResource(R.drawable.style_bottom_radius_for_cheque)
                        R.drawable.style_cheque_card_expandable
                    } else {
                        R.drawable.style_cheque_card_classic
                    }
                }
            )

            expandableButton.rotation = if (isExpanded) -90f else 90f
            fullInformationOfCheque.visibility = if (isExpanded) View.VISIBLE else View.GONE
        }


        private fun changeStyleLastClickedListElement() = with(previousClickedCheque) {
            binding.previewListChoiceChequeItem.setBackgroundResource(
                if (isExpanded) {
                    R.drawable.style_cheque_card_expandable
                } else {
                    R.drawable.style_cheque_card_classic
                }
            )

            binding.fullInformationOfCheque.setBackgroundResource(R.drawable.style_bottom_radius_for_cheque)
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

