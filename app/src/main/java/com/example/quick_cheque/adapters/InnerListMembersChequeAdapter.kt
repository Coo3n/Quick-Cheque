package com.example.quick_cheque.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.diff_utils.DiffUtilForInnerListMembersCheque
import com.example.quick_cheque.model.User

class InnerListMembersChequeAdapter(
    private var membersChequeList: MutableList<User>
) : RecyclerView.Adapter<InnerListMembersChequeAdapter.ListMembersChequeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMembersChequeViewHolder {
        return ListMembersChequeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_member_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListMembersChequeViewHolder, position: Int) {
        holder.bind(membersChequeList[position])
    }

    override fun getItemCount(): Int = membersChequeList.size

    fun addMemberInCheque(user: User) {
        membersChequeList.add(user)
        notifyItemInserted(membersChequeList.size - 1)
    }

    fun setListMemberCheque(newMembersChequeList: MutableList<User>) {
        val diffResult = DiffUtil.calculateDiff(
            DiffUtilForInnerListMembersCheque(
                membersChequeList,
                newMembersChequeList
            )
        )

        membersChequeList = newMembersChequeList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ListMembersChequeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconUserInMembersChequeList: ImageView =
            itemView.findViewById(R.id.iconUserInMembersChequeList)

        fun bind(user: User) {
            iconUserInMembersChequeList.setBackgroundResource(user.icon)
        }
    }
}