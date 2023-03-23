package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListAdapterWithDelegates
import com.example.quick_cheque.databinding.FragmentChoiceChequeBinding
import com.example.quick_cheque.delegates.Delegate
import com.example.quick_cheque.delegates.ExpandableListDelegate
import com.example.quick_cheque.list_items.ChequeListItem
import com.example.quick_cheque.list_items.ListItem
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.User
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ChoiceChequeFragment : Fragment() {
    private var binding: FragmentChoiceChequeBinding? = null
    private val _binding: FragmentChoiceChequeBinding
        get() = binding!!

    private lateinit var chequeRecyclerViewList: RecyclerView
    private lateinit var chequeRecyclerViewListAdapterWithDelegates: ListAdapterWithDelegates
    private lateinit var listItems: MutableList<ListItem>

    private val disposeBag = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChoiceChequeBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.buttonNextToDistributeCheque.setOnClickListener {
            Navigation.findNavController(_binding.root)
                .navigate(R.id.action_choiceChequeFragment_to_blankFragment)
        }

        listItems = getChequeList()

        setupChequeRecyclerViewList(
            delegates = listOf(ExpandableListDelegate()),
            listItems = listItems
        )

        disposeBag.add(
            RxTextView.textChanges(_binding.searchEditTextInCheque)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    Log.i("MyTag", it.toString())
                    filterSearchingItems(it.toString())
                }
        )
    }

    private fun filterSearchingItems(searchText: String) {
        val filteredListItems: MutableList<ListItem> =
            (listItems as MutableList<ChequeListItem>)
                .filter { item ->
                    item.cheque.title.lowercase().trim()
                        .contains(searchText.lowercase().trim())
                }
                .toMutableList()

        chequeRecyclerViewListAdapterWithDelegates.filterRecyclerViewListItems(filteredListItems)
    }

    private fun setupChequeRecyclerViewList(
        delegates: List<Delegate>,
        listItems: MutableList<ListItem>
    ) {
        chequeRecyclerViewList = _binding.chequeList

        chequeRecyclerViewList.setHasFixedSize(true)
        chequeRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())

        chequeRecyclerViewList.adapter = ListAdapterWithDelegates(
            delegates = delegates,
            listItems = listItems
        )

        chequeRecyclerViewListAdapterWithDelegates =
            chequeRecyclerViewList.adapter as ListAdapterWithDelegates
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
                    title = "Dii",
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
        disposeBag.clear()
        binding = null
        super.onDestroy()
    }
}