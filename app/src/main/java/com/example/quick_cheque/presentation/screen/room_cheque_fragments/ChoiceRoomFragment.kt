package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.data.mapper.toRoom
import com.example.quick_cheque.data.mapper.toRoomListItem
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.presentation.adapter.ListRoomAdapter
import com.example.quick_cheque.databinding.FragmentChoiceRoomBinding
import com.example.quick_cheque.domain.model.*
import com.example.quick_cheque.domain.repository.RoomRepository
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.example.quick_cheque.util.Resource
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class ChoiceRoomFragment : BaseFragment(), ListRoomAdapter.Clickable {
    private var binding: FragmentChoiceRoomBinding? = null
    private val _binding: FragmentChoiceRoomBinding
        get() = binding!!

    private val choiceItemViewModel: ChoiceItemViewModel by viewModels()

    private lateinit var roomRecyclerViewList: RecyclerView
    private lateinit var roomChequeAdapter: ListRoomAdapter

    @Inject
    lateinit var roomRepository: RoomRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity().application as MyApp).appComponent.injectChoiceRoomFragment(this)
        binding = FragmentChoiceRoomBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVisibleHomeButton(false)
        setVisibleToolBar()
        setupToolBar(R.menu.menu_with_search)


        if (choiceItemViewModel.listItems.value.size == 0) {
            binding?.rectangle1?.visibility = View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            roomRepository.getMyRooms(false).collect { result ->
                when (result) {
                    is Resource.Loading -> {

                    }
                    is Resource.Error -> {

                    }
                    is Resource.Success -> {
                        val b = result.data
                        choiceItemViewModel.setListItems(result.data as MutableList<ChoiceItem>)
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
        roomChequeAdapter.submitList(choiceItemViewModel.getFilteredListItems() as MutableList<RoomListItem>)
    }

    override fun onClick(position: Int) {
        choiceItemViewModel.setChoiceCurrentPosition(position)
    }

    override fun filterSearchingItems(query: String) {
        choiceItemViewModel.setFilteredListItems(
            choiceItemViewModel.listItems.value.filter { item ->
                val firstChequeTittle = item.getTitleItem().lowercase().trim()
                firstChequeTittle.contains(query.lowercase().trim())
            }.toMutableList()
        )

        roomChequeAdapter.submitList(choiceItemViewModel.getFilteredListItems() as MutableList<RoomListItem>)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}