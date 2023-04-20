package com.example.quick_cheque.screens.rooms_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListExpandableChoiceChequeAdapter
import com.example.quick_cheque.adapters.ListRoomAdapter
import com.example.quick_cheque.databinding.CardChoiceRoomItemBinding
import com.example.quick_cheque.databinding.FragmentChoiceChequeBinding
import com.example.quick_cheque.databinding.FragmentChoiceRoomBinding
import com.example.quick_cheque.model.*
import com.example.quick_cheque.screens.BaseFragment
import java.math.BigDecimal

class ChoiceRoomFragment : BaseFragment(), ListRoomAdapter.Clickable {
    private var binding: FragmentChoiceRoomBinding? = null
    private val _binding: FragmentChoiceRoomBinding
        get() = binding!!

    private lateinit var roomRecyclerViewList: RecyclerView
    private lateinit var roomChequeAdapter: ListRoomAdapter
    private lateinit var listItems: MutableList<RoomListItem>

    private var choiceCurrentPosition = 0

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
        listItems = getChequeList()
        setupRoomRecyclerViewList(listItems)

//        val toolbar = updateToolbar(
//            text = "Комнаты",
//            menu = R.menu.menu_with_search,
//        )
//
//        toolbar.apply {
//            setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.add_button -> {
//                        findNavController().navigate(R.id.action_choiceRoomFragment_to_createScreenFragment)
//                        true
//                    }
//                    else -> true
//                }
//            }
//
//            val mSearchView = menu.findItem(R.id.search_button)?.actionView as SearchView
//            mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?) = handleText(query)
//                override fun onQueryTextChange(newText: String?) = handleText(newText)
//
//                private fun handleText(text: String?): Boolean {
//                    text?.let { filterSearchingItems(it) }
//                    return true
//                }
//            })
//        }
    }

    private fun setupRoomRecyclerViewList(listItems: MutableList<RoomListItem>) {
        roomRecyclerViewList = _binding.chequeList
        roomRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())
        roomChequeAdapter = ListRoomAdapter(this)
        roomRecyclerViewList.adapter = roomChequeAdapter
        roomChequeAdapter.submitList(listItems)
    }

    override fun onClick(position: Int) {
        choiceCurrentPosition = position
    }

//    private fun filterSearchingItems(searchText: String) {
//        val filteredListItems: MutableList<RoomListItem> =
//            listItems.filter { item ->
//                val firstRoomTittle = item.room.title.lowercase().trim()
//                firstRoomTittle.contains(searchText.lowercase().trim())
//            }.toMutableList()
//
//        roomChequeAdapter.submitList(filteredListItems)
//    }

    private fun getChequeList(): MutableList<RoomListItem> {
        return mutableListOf(
            RoomListItem(
                room = Room(
                    id = 1,
                    title = "Крутецкая компания",
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