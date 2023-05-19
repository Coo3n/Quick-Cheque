package com.example.quick_cheque.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.CardChoiceRoomItemBinding
import com.example.quick_cheque.domain.model.RoomListItem
import com.example.quick_cheque.domain.model.User

class ListRoomAdapter : ListAdapter<RoomListItem, ListRoomAdapter.ListRoomViewHolder>(
    ListRoomDiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRoomViewHolder {
        return ListRoomViewHolder(
            CardChoiceRoomItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListRoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListRoomDiffCallBack : DiffUtil.ItemCallback<RoomListItem>() {
        override fun areItemsTheSame(oldItem: RoomListItem, newItem: RoomListItem): Boolean {
            return oldItem.room.id == newItem.room.id
        }

        override fun areContentsTheSame(oldItem: RoomListItem, newItem: RoomListItem): Boolean {
            return oldItem.room == newItem.room && oldItem.isExpanded == newItem.isExpanded
        }
    }


    inner class ListRoomViewHolder(
        private val binding: CardChoiceRoomItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var innerListMembersChequeAdapter: InnerListMembersChequeAdapter
        fun bind(room: RoomListItem) = with(binding) {
            changeExpandable(room.isExpanded)

            with(room.room) {
                roomTitle.text = title
                nameOwnerRoom.text = title
                chequeCount.text = cntCheques.toString()
                membersCount.text = membersRoom.size.toString()
                setupMembersRecyclerList(membersRoom)
            }

            with(binding.root) {
                roomTitle.setOnClickListener {
                    val bundle = Bundle().apply {
                        putParcelable("ROOM_TAG", room.room)
                    }

                    findNavController().navigate(
                        R.id.action_choiceRoomFragment_to_choiceChequeFragment,
                        bundle
                    )
                }
            }

            expandableButton.setOnClickListener {
                room.isExpanded = !room.isExpanded
                changeExpandable(room.isExpanded)
            }

            buttonAddNewMembersInRoom.setOnClickListener {
                innerListMembersChequeAdapter.addNewListMemberCheque(
                    User("Olya", R.drawable.person_filled)
                )
            }
        }

        private fun setupMembersRecyclerList(membersRoom: MutableList<User>) = with(binding) {
            listRoomMembers.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            innerListMembersChequeAdapter = InnerListMembersChequeAdapter()
            listRoomMembers.adapter = innerListMembersChequeAdapter
            innerListMembersChequeAdapter.submitList(membersRoom)
        }

        private fun changeExpandable(expanded: Boolean) = with(binding) {
            expandableButton.rotation = if (expanded) -90f else 90f
            fullInformationOfRoom.visibility = if (expanded) View.VISIBLE else View.GONE
        }
    }


}