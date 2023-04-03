package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListExpandableChoiceChequeAdapter
import com.example.quick_cheque.databinding.FragmentChoiceChequeBinding
import com.example.quick_cheque.model.ChequeListItem
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.Product
import com.example.quick_cheque.model.User
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class ChoiceChequeFragment : Fragment(), ListExpandableChoiceChequeAdapter.Clickable {
    private var binding: FragmentChoiceChequeBinding? = null
    private val _binding: FragmentChoiceChequeBinding
        get() = binding!!

    private lateinit var chequeExpandableRecyclerViewList: RecyclerView
    private lateinit var chequeExpandableChequeAdapter: ListExpandableChoiceChequeAdapter
    private lateinit var listItems: MutableList<ChequeListItem>

    private val disposeBag = CompositeDisposable()
    private var choiceCurrentPosition = 0

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
        listItems = getChequeList()

        setupChequeRecyclerViewList(listItems)

        disposeBag.add(
            RxTextView.textChanges(_binding.searchEditTextInCheque)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    filterSearchingItems(it.toString())
                }
        )

        _binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_choiceChequeFragment_to_mainScreenFragment)
        }

        _binding.buttonNextToDistributeCheque.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable("CHEQUE_TAG", (listItems[choiceCurrentPosition].cheque))
            }

            Navigation.findNavController(_binding.root).navigate(
                R.id.action_choiceChequeFragment_to_choiceProductFragment,
                bundle
            )
        }
    }


    private fun setupChequeRecyclerViewList(listItems: MutableList<ChequeListItem>) {
        chequeExpandableRecyclerViewList = _binding.chequeList
        chequeExpandableRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
        chequeExpandableChequeAdapter = ListExpandableChoiceChequeAdapter(this)
        chequeExpandableRecyclerViewList.adapter = chequeExpandableChequeAdapter
        chequeExpandableChequeAdapter.submitList(listItems)
    }

    override fun onClick(position: Int) {
        choiceCurrentPosition = position
    }

    private fun filterSearchingItems(searchText: String) {
        val filteredListItems: MutableList<ChequeListItem> =
            listItems.filter { item ->
                val firstChequeTittle = item.cheque.title.lowercase().trim()
                firstChequeTittle.contains(searchText.lowercase().trim())
            }.toMutableList()

        chequeExpandableChequeAdapter.submitList(filteredListItems)
    }

    private fun getChequeList(): MutableList<ChequeListItem> {
        return mutableListOf(
            ChequeListItem(
                Cheque(
                    title = "Valera",
                    owner = User("Zloi", R.drawable.person_filled),
                    sumOfCheque = BigDecimal(30),
                    products = mutableListOf(
                        Product(
                            titleProduct = "Кола",
                            price = BigDecimal(35),
                            count = 1,
                            membersProduct = mutableListOf(
                                User("Kolya", R.drawable.person_filled),
                                User("Olya", R.drawable.person_filled)
                            )
                        ),

                        Product(
                            titleProduct = "Кола",
                            price = BigDecimal(35),
                            count = 1
                        ),

                        Product(
                            titleProduct = "Кола",
                            price = BigDecimal(35),
                            count = 1
                        )
                    ),
                    membersCheque = mutableListOf(
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                    ),
                ),
                isExpanded = true
            ),

            ChequeListItem(
                Cheque(
                    title = "Valera",
                    owner = User("Zloi", R.drawable.person_filled),
                    products = mutableListOf(
                        Product(
                            titleProduct = "Чипсы",
                            price = BigDecimal(35),
                            count = 1,
                            membersProduct = mutableListOf(User("Zloi", R.drawable.person_filled))
                        ),

                        Product(
                            titleProduct = "Чипсы",
                            price = BigDecimal(35),
                            count = 1
                        )
                    ),
                ),
            ),

            ChequeListItem(
                Cheque(
                    title = "Dii",
                    owner = User("Zloi", R.drawable.person_filled),
                    sumOfCheque = BigDecimal(30),
                    membersCheque = mutableListOf(
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                    ),
                ),
            ),
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("tag", choiceCurrentPosition)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        choiceCurrentPosition = savedInstanceState?.getInt("tag") ?: 0
    }

    override fun onDestroy() {
        disposeBag.clear()
        binding = null
        super.onDestroy()
    }
}