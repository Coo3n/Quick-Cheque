package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
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
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    lateinit var roomRepositoryImpl: RoomRepositoryImpl

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

        val list = roomRepositoryImpl.getRooms().map {
            it.toRoom().toRoomListItem()
        }.toMutableList()

        choiceItemViewModel.setListItems(list as MutableList<ChoiceItem>)

        if (choiceItemViewModel.listItems.value.size != 0) {
            binding?.rectangle1?.visibility = View.GONE
        }

        if (isEmptyLastQuerySearch()) {
            choiceItemViewModel.setFilteredListItems(list as MutableList<ChoiceItem>)
        }

        setupRoomRecyclerViewList()

        activity?.findViewById<BottomNavigationView>(
            R.id.main_bottom_nav
        )?.visibility = View.VISIBLE
    }

    override fun handleAddButtonClicked() {
        val v = requireActivity().findViewById<View>(R.id.add_button)
        val popup = PopupMenu(v.context, v)
        popup.inflate(R.menu.menu_popup_add)
        popup.show()

//        val dialogBinding = layoutInflater.inflate(R.layout.add_cheque_alert_dialog, null)
//        val myDialog = Dialog(requireContext()).apply {
//            setContentView(dialogBinding)
//            setCancelable(true)
//            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//            show()
//        }
//
//        myDialog.findViewById<Button>(R.id.alert_button_add_cheque).setOnClickListener {
//
//        }


//        roomRepositoryImpl.insertRoom(
//            RoomEntity(
//                titleRoom = "ilya",
//                ownerId = 123
//            )
//        )
    }

    private fun setupRoomRecyclerViewList() {
        roomRecyclerViewList = _binding.chequeList
        roomRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
        roomChequeAdapter = ListRoomAdapter(this)
        roomRecyclerViewList.adapter = roomChequeAdapter
        roomChequeAdapter.submitList(choiceItemViewModel.filteredListItems.value as MutableList<RoomListItem>)
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

        roomChequeAdapter.submitList(choiceItemViewModel.filteredListItems.value as MutableList<RoomListItem>)
    }

    private fun getChequeList(): MutableList<ChoiceItem> {
        return mutableListOf(
            RoomListItem(
                room = Room(
                    id = 1,
                    title = "Крутецкая",
                    host = "Никитос",
                    membersRoom = mutableListOf(
                        User("Kolya", R.drawable.person_filled),
                        User("Olya", R.drawable.person_filled)
                    ),
                    cheques = mutableListOf(
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
                            )
                        ),
                        Cheque(
                            title = "Valera",
                            owner = User("Zloi", R.drawable.person_filled),
                            products = mutableListOf(
                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1,
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
                            ),
                        ),
                    ),
                )
            ),
            RoomListItem(
                room = Room(
                    id = 2,
                    title = "Каша малаша",
                    host = "Ну я хостом буду",
                    membersRoom = mutableListOf(
                        User("Kolya", R.drawable.person_filled),
                        User("Olya", R.drawable.person_filled),
                        User("Ibragym", R.drawable.person_filled),
                        User("Svyat", R.drawable.person_filled)
                    ),
                    cheques = mutableListOf(
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
                            )
                        ),
                        Cheque(
                            title = "Valera",
                            owner = User("Zloi", R.drawable.person_filled),
                            products = mutableListOf(
                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1,
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Кока",
                                    price = BigDecimal(35),
                                    count = 1
                                )
                            ),
                        ),
                        Cheque(
                            title = "Valera",
                            owner = User("Zloi", R.drawable.person_filled),
                            products = mutableListOf(
                                Product(
                                    titleProduct = "Кока",
                                    price = BigDecimal(35),
                                    count = 1,
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Кока",
                                    price = BigDecimal(35),
                                    count = 1
                                )
                            ),
                        ),
                        Cheque(
                            title = "Valera",
                            owner = User("Zloi", R.drawable.person_filled),
                            products = mutableListOf(
                                Product(
                                    titleProduct = "Кока",
                                    price = BigDecimal(35),
                                    count = 1,
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Кока",
                                    price = BigDecimal(35),
                                    count = 1
                                )
                            ),
                        ),
                    ),
                ),
                isExpanded = false
            ),
            RoomListItem(
                room = Room(
                    id = 3,
                    title = "А я опять затуплю...",
                    host = "Самый лысый",
                    membersRoom = mutableListOf(
                        User("Ignat", R.drawable.person_filled),
                        User("Ignat", R.drawable.person_filled),
                        User("Ignat", R.drawable.person_filled),
                        User("Ignat", R.drawable.person_filled),
                        User("Ignat", R.drawable.person_filled),
                        User("Ignat", R.drawable.person_filled),
                        User("Ignat", R.drawable.person_filled),
                    ),
                    cheques = mutableListOf(
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
                            )
                        ),
                        Cheque(
                            title = "Valera",
                            owner = User("Zloi", R.drawable.person_filled),
                            products = mutableListOf(
                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1,
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
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
                                    membersProduct = mutableListOf(
                                        User(
                                            "Zloi",
                                            R.drawable.person_filled
                                        )
                                    )
                                ),

                                Product(
                                    titleProduct = "Чипсы",
                                    price = BigDecimal(35),
                                    count = 1
                                )
                            ),
                        ),
                    ),
                ),
                isExpanded = false
            )
        )
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}