package com.example.quick_cheque.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.model.User

class InnerListMembersChequeAdapter(
    var listUsers: List<User>
) : RecyclerView.Adapter<InnerListMembersChequeAdapter.ListMembersChequeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMembersChequeViewHolder {
        return ListMembersChequeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_members, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListMembersChequeViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = listUsers.size

    inner class ListMembersChequeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}