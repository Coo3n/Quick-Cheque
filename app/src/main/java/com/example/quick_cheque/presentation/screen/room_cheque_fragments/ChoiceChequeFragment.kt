package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.data.repository.ChequeRepositoryImpl
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.presentation.adapter.ListExpandableChoiceChequeAdapter
import com.example.quick_cheque.databinding.FragmentChoiceChequeBinding
import com.example.quick_cheque.domain.model.*
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.example.quick_cheque.presentation.screen.viewmodel.ChoiceChequeViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class ChoiceChequeFragment : BaseFragment(), ListExpandableChoiceChequeAdapter.Clickable {
    private var binding: FragmentChoiceChequeBinding? = null
    private val _binding: FragmentChoiceChequeBinding
        get() = binding!!

    private lateinit var choiceChequeViewModel: ChoiceChequeViewModel

    private lateinit var chequeExpandableRecyclerViewList: RecyclerView
    private lateinit var chequeExpandableChequeAdapter: ListExpandableChoiceChequeAdapter

    @Inject
    lateinit var chequeRepository: ChequeRepositoryImpl

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.injectChoiceChequeFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ChoiceChequeViewModel.ChoiceChequeViewModelFactory(chequeRepository)

        choiceChequeViewModel = ViewModelProvider(
            this,
            factory
        )[ChoiceChequeViewModel::class.java]
    }

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

        Log.i("room_id", arguments?.getInt("ROOM_ID").toString())

        choiceChequeViewModel.setListItems(getChequeList())
        if (isEmptyLastQuerySearch()) {
            choiceChequeViewModel.setFilteredListItems(choiceChequeViewModel.listItems.value)
        }

        setupChequeRecyclerViewList()

        _binding.buttonNextToDistributeCheque.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(
                    "CHEQUE_TAG",
                    (choiceChequeViewModel.getChoiceItemOnPosition(
                        choiceChequeViewModel.choiceLastPosition.value
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

        (choiceChequeViewModel.getChoiceItemOnPosition(
            choiceChequeViewModel.choiceLastPosition.value
        ) as ChequeListItem).isClicked = true

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                choiceChequeViewModel.filteredListItems.collect { choiceItemList ->
                    chequeExpandableChequeAdapter.submitList(choiceItemList as List<ChequeListItem>)
                }
            }
        }
    }

    override fun onClick(position: Int) {
        val prevItemChanged = (choiceChequeViewModel.getChoiceItemOnPosition(
            choiceChequeViewModel.choiceLastPosition.value
        ) as ChequeListItem)

        val currentClickedItem = (choiceChequeViewModel.getChoiceItemOnPosition(
            position
        ) as ChequeListItem)

        choiceChequeViewModel.changeChoiceItemState(
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

        choiceChequeViewModel.setChoiceCurrentPosition(position)
    }

    override fun filterSearchingItems(query: String) {
        choiceChequeViewModel.setFilteredListItems(
            choiceChequeViewModel.listItems.value.filter { item ->
                val firstChequeTittle = item.getTitleItem().lowercase().trim()
                firstChequeTittle.contains(query.lowercase().trim())
            }.toMutableList()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                choiceChequeViewModel.filteredListItems.collect {
                    chequeExpandableChequeAdapter.submitList(it)
                }
            }
        }
    }

    private fun getChequeList(): MutableList<ChequeListItem> {
        return mutableListOf(
            ChequeListItem(
                Cheque(
                    id = 1,
                    title = "Valera",
                    owner = User("Olua", "es", R.drawable.person_filled),
                    sumOfCheque = BigDecimal(30),
                    membersCheque = mutableListOf(
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                    ),
                )
            ),

            ChequeListItem(
                Cheque(
                    id = 2,
                    title = "Valera",
                    owner = User("Olua", "es", R.drawable.person_filled),
                )

            ),

            ChequeListItem(
                Cheque(
                    id = 3,
                    title = "Dii",
                    owner = User("Olua", "es", R.drawable.person_filled),
                    sumOfCheque = BigDecimal(30),
                    membersCheque = mutableListOf(
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                    ),
                ),
            ),

            ChequeListItem(
                Cheque(
                    id = 3,
                    title = "Dii",
                    owner = User("Olua", "es", R.drawable.person_filled),
                    sumOfCheque = BigDecimal(30),
                    membersCheque = mutableListOf(
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled)
                    ),
                ),
            ),

            ChequeListItem(
                Cheque(
                    id = 3,
                    title = "Dii",
                    owner = User("Olua", "es", R.drawable.person_filled),
                    sumOfCheque = BigDecimal(30),
                    membersCheque = mutableListOf(
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
                        User("Olua", "es", R.drawable.person_filled),
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