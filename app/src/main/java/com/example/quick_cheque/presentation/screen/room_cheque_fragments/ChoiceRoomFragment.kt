package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.data.mapper.toRoomListItem
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.presentation.adapter.ListRoomAdapter
import com.example.quick_cheque.databinding.FragmentChoiceRoomBinding
import com.example.quick_cheque.domain.model.*
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChoiceRoomFragment : BaseFragment(), ListRoomAdapter.Clickable {
    private var binding: FragmentChoiceRoomBinding? = null
    private val _binding: FragmentChoiceRoomBinding
        get() = binding!!

    private lateinit var choiceItemViewModelFactory: ChoiceItemViewModelFactory
    private lateinit var choiceItemViewModel: ChoiceItemViewModel

    private lateinit var roomRecyclerViewList: RecyclerView
    private lateinit var roomChequeAdapter: ListRoomAdapter

    @Inject
    lateinit var roomRepository: RoomRepositoryImpl

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.injectChoiceRoomFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        choiceItemViewModelFactory = ChoiceItemViewModelFactory(roomRepository)
        choiceItemViewModel = ViewModelProvider(
            this,
            choiceItemViewModelFactory
        )[ChoiceItemViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChoiceRoomBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVisibleHomeButton(false)
        setVisibleToolBar()
        setupToolBar(R.menu.menu_with_search)

        choiceItemViewModel.initChoiceItems()

        if (choiceItemViewModel.listItems.value.isNotEmpty()) {
            binding?.rectangle1?.visibility = View.GONE
        }

        _binding.refresher.setOnRefreshListener {
            choiceItemViewModel.onEvent(ChoiceItemEvent.Refresh)
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    choiceItemViewModel.choiceItemState.collect { state ->
                        if (!state.isLoading) {
                            _binding.refresher.isRefreshing = false
                        }
                    }
                }
            }
        }

        setupRoomRecyclerViewList()
        activity?.findViewById<BottomNavigationView>(
            R.id.main_bottom_nav
        )?.visibility = View.VISIBLE
    }

    override fun handleAddButtonClicked() {
        val addButtonTopBar = requireActivity().findViewById<View>(R.id.add_button)
        val popupMenu = PopupMenu(addButtonTopBar.context, addButtonTopBar)
        popupMenu.inflate(R.menu.menu_popup_add)
        popupMenu.show()
    }

    private fun setupRoomRecyclerViewList() {
        roomRecyclerViewList = _binding.chequeList
        roomRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
        roomChequeAdapter = ListRoomAdapter(this)
        roomRecyclerViewList.adapter = roomChequeAdapter

        choiceItemViewModel.onEvent(ChoiceItemEvent.Refresh)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                choiceItemViewModel.filteredListItems.collect { choiceItemList ->
                    roomChequeAdapter.submitList((choiceItemList as MutableList<Room>).map { it.toRoomListItem() })
                }
            }
        }
    }

    override fun onClick(position: Int) {
        choiceItemViewModel.setChoiceLastPosition(position)
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
                    roomChequeAdapter.submitList(
                        (choiceItemViewModel.filteredListItems.value as MutableList<Room>).map {
                            it.toRoomListItem()
                        }
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}