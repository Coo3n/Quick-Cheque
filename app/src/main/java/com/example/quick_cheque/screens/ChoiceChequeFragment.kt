package com.example.quick_cheque.screens

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListAdapterWithDelegates
import com.example.quick_cheque.databinding.ChoiceChequeFragmentBinding
import com.example.quick_cheque.delegates.ExpandableListDelegate
import com.example.quick_cheque.list_items.ChequeListItem
import com.example.quick_cheque.list_items.ListItem
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.User


class ChoiceChequeFragment : Fragment() {
    private var binding: ChoiceChequeFragmentBinding? = null
    private val _binding: ChoiceChequeFragmentBinding
        get() = binding!!

    private lateinit var chequeRecyclerViewList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChoiceChequeFragmentBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chequeRecyclerViewList = _binding.chequeList
        chequeRecyclerViewList.setHasFixedSize(true)
        chequeRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())

        chequeRecyclerViewList.adapter = ListAdapterWithDelegates(
            delegates = listOf(ExpandableListDelegate()),
            listItems = getChequeList()
        )
    }

    private fun getChequeList(): MutableList<ListItem> {
        return mutableListOf(
            ChequeListItem(
                Cheque(
                    title = "Valera",
                    owner = User("Zloi", R.drawable.cheque),
                    sumOfCheque = 30,
                    membersCheque = mutableListOf(
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                    ),
                ),
                isExpanded = true
            ),

            ChequeListItem(
                Cheque(
                    title = "Valera",
                    owner = User("Zloi", R.drawable.cheque),
                ),
            ),

            ChequeListItem(
                Cheque(
                    title = "Valera",
                    owner = User("Zloi", R.drawable.cheque),
                    sumOfCheque = 30,
                    membersCheque = mutableListOf(
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                        User("ZA", R.drawable.cheque),
                    ),
                ),
            ),
        )
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}