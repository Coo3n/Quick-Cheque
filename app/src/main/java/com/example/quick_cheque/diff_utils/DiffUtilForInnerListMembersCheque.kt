package com.example.quick_cheque.diff_utils

import androidx.recyclerview.widget.DiffUtil
import com.example.quick_cheque.model.User

class DiffUtilForInnerListMembersCheque(
    private val oldMembersChequeList: List<User>,
    private val newMembersChequeList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldMembersChequeList.size

    override fun getNewListSize(): Int = newMembersChequeList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMembersChequeList[oldItemPosition].name == newMembersChequeList[newItemPosition].name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMembersChequeList[oldItemPosition] == newMembersChequeList[newItemPosition]
    }
}