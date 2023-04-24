package com.example.quick_cheque.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.databinding.ListMemberItemBinding
import com.example.quick_cheque.domain.model.User

class InnerListMembersChequeAdapter :
    ListAdapter<User, InnerListMembersChequeAdapter.ListMembersChequeViewHolder>(
        InnerListMembersChequeDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMembersChequeViewHolder {
        return ListMembersChequeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ListMembersChequeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun addNewListMemberCheque(user: User) {
        val newList = currentList.toMutableList()
        newList.add(user)
        submitList(newList)
    }

    class InnerListMembersChequeDiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    class ListMembersChequeViewHolder(
        private val binding: ListMemberItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) = with(binding) {
            iconUserInMembersChequeList.setBackgroundResource(user.icon)
        }

        companion object {
            fun create(parent: ViewGroup): ListMembersChequeViewHolder {
                return ListMembersChequeViewHolder(
                    ListMemberItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}