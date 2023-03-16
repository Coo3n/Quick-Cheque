package com.example.quick_cheque

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_cheque.adapters.ListAdapterWithDelegates
import com.example.quick_cheque.databinding.FragmentReceiptWindowBinding
import com.example.quick_cheque.delegates.ExpandableListDelegate
import com.example.quick_cheque.expandable_list_items.ChequeListItem
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.User


class ReceiptWindowFragment : Fragment() {
    private var binding: FragmentReceiptWindowBinding? = null
    private val _binding: FragmentReceiptWindowBinding
        get() = binding!!


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
        val recyclerView = _binding.chequeList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = ListAdapterWithDelegates(
            delegates = listOf(ExpandableListDelegate()),
            listItems = listOf(
                ChequeListItem(
                    Cheque(
                        title = "Ilya",
                        owner = User("Valera"),
                        sumOfCheque = 30,
                    ),
                    isExpanded = true
                )
            )
        )

    }


    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}