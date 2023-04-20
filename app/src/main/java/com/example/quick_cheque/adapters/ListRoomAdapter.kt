package com.example.quick_cheque.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.CardChoiceProductItemBinding
import com.example.quick_cheque.databinding.CardChoiceRoomItemBinding
import com.example.quick_cheque.model.Product
import com.example.quick_cheque.model.Room
import com.example.quick_cheque.model.RoomListItem
import com.example.quick_cheque.model.User
import androidx.navigation.fragment.findNavController

open class ListRoomAdapter(private val clickable: Clickable) :
    ListAdapter<RoomListItem, ListRoomAdapter.ListRoomViewHolder>(
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

    interface Clickable {
        fun onClick(position: Int)
    }

    override fun onBindViewHolder(holder: ListRoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListRoomDiffCallBack : DiffUtil.ItemCallback<RoomListItem>() {
        override fun areItemsTheSame(oldItem: RoomListItem, newItem: RoomListItem): Boolean {
            return oldItem == newItem
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
            changeExpandencity(
                room.isExpanded
            )

            with(room.room) {
                roomTitle.text = title
                nameOwnerRoom.text = host
                chequeCount.text = cheques.size.toString()
                membersCount.text = membersRoom.size.toString()
                setupMembersRecyclerList(membersRoom)
            }

            with(binding.root) {
                roomTitle.setOnClickListener {
                    val bundle = Bundle().apply {
                        putParcelable("ROOM_TAG", (room.room))
                    }

                    findNavController().navigate(
                        R.id.action_choiceRoomFragment_to_choiceChequeFragment,
                        bundle
                    )
                }
            }

            expandableButton.setOnClickListener {
                room.isExpanded = !room.isExpanded
                changeExpandencity(
                    room.isExpanded
                )
            }

            buttonAddNewMembersInRoom.setOnClickListener {
                innerListMembersChequeAdapter.addNewListMemberCheque(
                    User("Olya", R.drawable.person_filled)
                )
            }

            clickable.onClick(adapterPosition)
        }

        private fun setupMembersRecyclerList(membersRoom: MutableList<User>) = with(binding) {
            listRoomMembers.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            innerListMembersChequeAdapter = InnerListMembersChequeAdapter()
            listRoomMembers.adapter = innerListMembersChequeAdapter
            innerListMembersChequeAdapter.submitList(membersRoom)
        }

        private fun changeExpandencity(expanded: Boolean) = with(binding) {
            expandableButton.rotation = if (expanded) -90f else 90f
            fullInformationOfRoom.visibility = if (expanded) View.VISIBLE else View.GONE
        }
    }


}