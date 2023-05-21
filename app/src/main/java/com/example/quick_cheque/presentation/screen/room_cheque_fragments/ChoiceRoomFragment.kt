package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.quick_cheque.presentation.events.ChoiceItemEvent
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.example.quick_cheque.presentation.screen.viewmodel.ChoiceRoomViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChoiceRoomFragment : BaseFragment() {
    private var binding: FragmentChoiceRoomBinding? = null
    private val _binding: FragmentChoiceRoomBinding
        get() = binding!!

    private lateinit var choiceRoomViewModel: ChoiceRoomViewModel

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
        setVisibleToolBar()
        val factory = ChoiceRoomViewModel.ChoiceItemViewModelFactory(roomRepository)
        choiceRoomViewModel = ViewModelProvider(
            this,
            factory
        )[ChoiceRoomViewModel::class.java]
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
        setupToolBar(R.menu.menu_with_search)

        if (choiceRoomViewModel.getListRoomItem().isNotEmpty()) {
            binding?.rectangle1?.visibility = View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                choiceRoomViewModel.roomListItems.collect { item ->
                    if (item.isNotEmpty()) {
                        binding?.rectangle1?.visibility = View.GONE
                    }
                }
            }
        }

        _binding.refresher.setOnRefreshListener {
            choiceRoomViewModel.onEvent(ChoiceItemEvent.Refresh)
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    choiceRoomViewModel.roomItemState.collect { state ->
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

    private fun setupRoomRecyclerViewList() {
        roomRecyclerViewList = _binding.chequeList
        roomRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
        roomChequeAdapter = ListRoomAdapter()
        roomRecyclerViewList.adapter = roomChequeAdapter

        choiceRoomViewModel.onEvent(ChoiceItemEvent.Refresh)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                choiceRoomViewModel.filteredRoomListItems.collect { choiceItemList ->
                    roomChequeAdapter.submitList(choiceItemList.map { it.toRoomListItem() })
                }
            }
        }
    }

    override fun filterSearchingItems(query: String) {
        choiceRoomViewModel.setFilteredListItems(
            choiceRoomViewModel.getListRoomItem().filter { item ->
                val firstChequeTittle = item.getTitleItem().lowercase().trim()
                firstChequeTittle.contains(query.lowercase().trim())
            }.toMutableList()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                choiceRoomViewModel.filteredRoomListItems.collect {
                    roomChequeAdapter.submitList(
                        choiceRoomViewModel.getFilteredListRoomItem().map {
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