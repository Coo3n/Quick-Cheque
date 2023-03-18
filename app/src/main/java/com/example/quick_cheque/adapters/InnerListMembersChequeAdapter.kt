package com.example.quick_cheque.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.model.User

class InnerListMembersChequeAdapter(
    private val membersChequeList: MutableList<User>
) : RecyclerView.Adapter<InnerListMembersChequeAdapter.ListMembersChequeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMembersChequeViewHolder {
        return ListMembersChequeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_members, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListMembersChequeViewHolder, position: Int) {
        holder.bind(membersChequeList[position])
    }

    override fun getItemCount(): Int = membersChequeList.size

    inner class ListMembersChequeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconUserInMembersChequeList: ImageView =
            itemView.findViewById(R.id.iconUserInMembersChequeList)

        fun bind(user: User) {
            iconUserInMembersChequeList.setBackgroundResource(user.icon)
        }
    }
}