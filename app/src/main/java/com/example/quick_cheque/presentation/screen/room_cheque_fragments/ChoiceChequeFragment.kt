package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.presentation.adapter.ListExpandableChoiceChequeAdapter
import com.example.quick_cheque.databinding.FragmentChoiceChequeBinding
import com.example.quick_cheque.domain.model.*
import com.example.quick_cheque.presentation.screen.BaseFragment
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ChoiceChequeFragment : BaseFragment(), ListExpandableChoiceChequeAdapter.Clickable {
    private var binding: FragmentChoiceChequeBinding? = null
    private val _binding: FragmentChoiceChequeBinding
        get() = binding!!

    private val choiceItemViewModel: ChoiceItemViewModel by viewModels()

    private lateinit var chequeExpandableRecyclerViewList: RecyclerView
    private lateinit var chequeExpandableChequeAdapter: ListExpandableChoiceChequeAdapter

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
        setVisibleToolBar()
        setupToolBar(R.menu.menu_with_search)

        choiceItemViewModel.setListItems(getChequeList())
        if (isEmptyLastQuerySearch()) {
            choiceItemViewModel.setFilteredListItems(choiceItemViewModel.listItems.value)
        }

        setupChequeRecyclerViewList()

        _binding.buttonNextToDistributeCheque.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(
                    "CHEQUE_TAG",
                    (choiceItemViewModel.getChoiceItemOnPosition(
                        choiceItemViewModel.choiceLastPosition.value
                    ) as ChequeListItem).cheque
                )
            }

            findNavController().navigate(
                R.id.action_choiceChequeFragment_to_choiceProductFragment,
                bundle
            )
        }
    }

    private fun setupChequeRecyclerViewList() {
        chequeExpandableRecyclerViewList = _binding.chequeList
        chequeExpandableRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
        chequeExpandableChequeAdapter = ListExpandableChoiceChequeAdapter(this)
        chequeExpandableRecyclerViewList.adapter = chequeExpandableChequeAdapter

        (choiceItemViewModel.getChoiceItemOnPosition(
            choiceItemViewModel.choiceLastPosition.value
        ) as ChequeListItem).isClicked = true

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                choiceItemViewModel.filteredListItems.collect { choiceItemList ->
                    chequeExpandableChequeAdapter.submitList(choiceItemList as List<ChequeListItem>)
                }
            }
        }
    }

    override fun onClick(position: Int) {
        val prevItemChanged = (choiceItemViewModel.getChoiceItemOnPosition(
            choiceItemViewModel.choiceLastPosition.value
        ) as ChequeListItem)

        val currentClickedItem = (choiceItemViewModel.getChoiceItemOnPosition(
            position
        ) as ChequeListItem)

        choiceItemViewModel.changeChoiceItemState(
            position,
            prevItemChanged.copy(
                isClicked = false,
                isExpanded = prevItemChanged.isExpanded
            ),
            currentClickedItem.copy(
                isClicked = true,
                isExpanded = currentClickedItem.isExpanded
            ),
        )

        choiceItemViewModel.setChoiceCurrentPosition(position)
    }

    override fun filterSearchingItems(query: String) {
        choiceItemViewModel.setFilteredListItems(
            choiceItemViewModel.listItems.value.filter { item ->
                val firstChequeTittle = item.getTitleItem().lowercase().trim()
                firstChequeTittle.contains(query.lowercase().trim())
            }.toMutableList()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                choiceItemViewModel.filteredListItems.collect {
                    chequeExpandableChequeAdapter.submitList(it as List<ChequeListItem>)
                }
            }
        }
    }

    private fun getChequeList(): MutableList<ChoiceItem> {
        return mutableListOf(
            ChequeListItem(
                Cheque(
                    id = 1,
                    title = "Valera",
                    owner = User("Zloi", R.drawable.person_filled),
                    sumOfCheque = BigDecimal(30),
                    products = mutableListOf(
                        Product(
                            id = 11,
                            titleProduct = "Кола",
                            price = BigDecimal(35),
                            count = 1,
                            membersProduct = mutableListOf(
                                User("Kolya", R.drawable.person_filled),
                                User("Olya", R.drawable.person_filled)
                            )
                        )
                    ),
                    membersCheque = mutableListOf(
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                        User("ZA", R.drawable.person_filled),
                    ),
                )
            ),

            ChequeListItem(
                Cheque(
                    id = 2,
                    title = "Valera",
                    owner = User("Zloi", R.drawable.person_filled),
                    products = mutableListOf(
                        Product(
                            id = 21,
                            titleProduct = "Чипсы",
                            price = BigDecimal(35),
                            count = 1,
                            membersProduct = mutableListOf(User("Zloi", R.drawable.person_filled))
                        ),

                        Product(
                            id = 23,
                            titleProduct = "Бутер",
                            price = BigDecimal(35),
                            count = 1
                        )
                    ),
                )
            ),

            ChequeListItem(
                Cheque(
                    id = 3,
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

            ChequeListItem(
                Cheque(
                    id = 3,
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

            ChequeListItem(
                Cheque(
                    id = 3,
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
            )
        )

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}