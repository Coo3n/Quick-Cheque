package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListExpandableChoiceChequeAdapter
import com.example.quick_cheque.databinding.FragmentChoiceChequeBinding
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.ChequeListItem
import com.example.quick_cheque.model.Product
import com.example.quick_cheque.model.User
import com.example.quick_cheque.screens.BaseFragment
import java.math.BigDecimal

class ChoiceChequeFragment : BaseFragment(), ListExpandableChoiceChequeAdapter.Clickable {
    private var binding: FragmentChoiceChequeBinding? = null
    private val _binding: FragmentChoiceChequeBinding
        get() = binding!!

    private lateinit var viewModel: ChoiceChequeViewModel

    private lateinit var chequeExpandableRecyclerViewList: RecyclerView
    private lateinit var chequeExpandableChequeAdapter: ListExpandableChoiceChequeAdapter
    private lateinit var listItems: MutableList<ChequeListItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ChoiceChequeViewModel::class.java]
        binding = FragmentChoiceChequeBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolBar(R.menu.menu_with_search)

        listItems = getChequeList()
        setupChequeRecyclerViewList(listItems)

        _binding.buttonNextToDistributeCheque.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(
                    "CHEQUE_TAG", (listItems[viewModel.choiceCurrentPosition.value].cheque)
                )
            }

            findNavController().navigate(
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
        viewModel.setChoiceCurrentPosition(position)
    }

    override fun filterSearchingItems(query: String) {
        val filteredListItems: MutableList<ChequeListItem> =
            listItems.filter { item ->
                val firstChequeTittle = item.cheque.title.lowercase().trim()
                firstChequeTittle.contains(query.lowercase().trim())
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

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}