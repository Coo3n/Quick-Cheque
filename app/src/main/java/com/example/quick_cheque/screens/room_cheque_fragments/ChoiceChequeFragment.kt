package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
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
import com.example.quick_cheque.screens.BaseFragment
import io.reactivex.disposables.CompositeDisposable
import java.math.BigDecimal

class ChoiceChequeFragment : BaseFragment(), ListExpandableChoiceChequeAdapter.Clickable {
    private var binding: FragmentChoiceChequeBinding? = null
    private val _binding: FragmentChoiceChequeBinding
        get() = binding!!

    private lateinit var chequeExpandableRecyclerViewList: RecyclerView
    private lateinit var chequeExpandableChequeAdapter: ListExpandableChoiceChequeAdapter
    private lateinit var listItems: MutableList<ChequeListItem>

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

        val toolbar = updateToolbar(
            text = "Чек",
            menu = R.menu.menu_with_search,
        )

        toolbar.apply {
            setNavigationOnClickListener {
                findNavController().navigate(R.id.action_choiceChequeFragment_to_mainScreenFragment)
            }

            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.add_button -> {
                        Toast.makeText(requireContext(), "Добавить", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> {
                        Log.i("MyTag", item.itemId.toString())
                        true
                    }
                }
            }

            val mSearchView = menu.findItem(R.id.search_button)?.actionView as SearchView
            mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?) = handleText(query)
                override fun onQueryTextChange(newText: String?) = handleText(newText)

                private fun handleText(text: String?): Boolean {
                    text?.let { filterSearchingItems(it) }
                    return true
                }
            })
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

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}