package com.example.quick_cheque.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.adapters.ListAdapterWithDelegates
import com.example.quick_cheque.databinding.FragmentReceiptWindowBinding
import com.example.quick_cheque.delegates.ExpandableListDelegate
import com.example.quick_cheque.list_items.ChequeListItem
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.User


class ReceiptWindowFragment : Fragment() {
    private var binding: FragmentReceiptWindowBinding? = null
    private val _binding: FragmentReceiptWindowBinding
        get() = binding!!

    private lateinit var chequeRecyclerViewList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReceiptWindowBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chequeRecyclerViewList = _binding.chequeList
        chequeRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())

        chequeRecyclerViewList.adapter = ListAdapterWithDelegates(
            delegates = listOf(ExpandableListDelegate()),
            listItems = getChequeList()
        )
    }

    private fun getChequeList(): List<ChequeListItem> {
        return listOf(
            ChequeListItem(
                Cheque(
                    title = "",
                    owner = User("Valera"),
                    sumOfCheque = 30,
                ),
                isExpanded = true
            ),

            ChequeListItem(
                Cheque(
                    title = "Ilya",
                    owner = User("Valera"),
                    sumOfCheque = 30,
                )
            ),

            ChequeListItem(
                Cheque(
                    title = "Ilya",
                    owner = User("Valera"),
                    sumOfCheque = 30,
                )
            )
        )
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}