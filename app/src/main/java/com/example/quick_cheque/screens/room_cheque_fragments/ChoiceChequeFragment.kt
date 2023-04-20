package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListExpandableChoiceChequeAdapter
import com.example.quick_cheque.databinding.FragmentChoiceChequeBinding
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.Product
import com.example.quick_cheque.model.User
import com.example.quick_cheque.screens.BaseFragment
import com.example.quick_cheque.screens.viewmodels.ChoiceChequeViewModel
import java.math.BigDecimal

class ChoiceChequeFragment : BaseFragment(), ListExpandableChoiceChequeAdapter.Clickable {
    private var binding: FragmentChoiceChequeBinding? = null
    private val _binding: FragmentChoiceChequeBinding
        get() = binding!!

    private val viewModel: ChoiceChequeViewModel by viewModels()

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

        setupToolBar(R.menu.menu_with_search)
        setVisibleToolBar()
        viewModel.setListItems(getChequeList())
        if (viewModel.lastQuerySearch.value.isEmpty()) {
            viewModel.setFilteredListItems(getChequeList())
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.filteredListItems.collect {
//
//                }
//            }
//        }

        setupChequeRecyclerViewList()

        _binding.buttonNextToDistributeCheque.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(
                    "CHEQUE_TAG", (viewModel.listItems.value[viewModel.choiceCurrentPosition.value])
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
        chequeExpandableChequeAdapter.submitList(viewModel.filteredListItems.value)
    }

    override fun onClick(position: Int) {
        viewModel.setChoiceCurrentPosition(position)
    }

    override fun filterSearchingItems(query: String) {
        viewModel.setFilteredListItems(
            viewModel.listItems.value.filter { item ->
                val firstChequeTittle = item.title.lowercase().trim()
                firstChequeTittle.contains(query.lowercase().trim())
            }.toMutableList()
        )

        viewModel.setLastQuerySearch(query)
        chequeExpandableChequeAdapter.submitList(viewModel.filteredListItems.value)
    }

    private fun getChequeList(): MutableList<Cheque> {
        return mutableListOf(
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
                    )
                ),
                membersCheque = mutableListOf(
                    User("ZA", R.drawable.person_filled),
                    User("ZA", R.drawable.person_filled),
                    User("ZA", R.drawable.person_filled),
                ),
            ),


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
        )
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}