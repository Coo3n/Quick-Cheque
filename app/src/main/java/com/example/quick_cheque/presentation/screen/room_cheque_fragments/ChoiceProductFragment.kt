package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.presentation.adapter.ListProductsAdapter
import com.example.quick_cheque.databinding.FragmentChoiceProductBinding
import com.example.quick_cheque.domain.model.Cheque
import com.example.quick_cheque.domain.model.ChoiceItem
import com.example.quick_cheque.domain.model.Product
import com.example.quick_cheque.presentation.screen.BaseFragment
import javax.inject.Inject


class ChoiceProductFragment : BaseFragment() {
    private var binding: FragmentChoiceProductBinding? = null
    private val _binding: FragmentChoiceProductBinding
        get() = binding!!

    private lateinit var choiceItemViewModelFactory: ChoiceItemViewModelFactory
    private lateinit var choiceItemViewModel: ChoiceItemViewModel

    private var transmittedCheque: Cheque? = null
    private lateinit var recyclerViewListProductsAdapter: ListProductsAdapter

    @Inject
    lateinit var roomRepository: RoomRepositoryImpl

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.injectChoiceProductFragment(this)
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChoiceProductBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar(R.menu.menu_with_search)

        transmittedCheque = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("CHEQUE_TAG", Cheque::class.java) ?: transmittedCheque
        } else {
            arguments?.getParcelable("CHEQUE_TAG") ?: transmittedCheque
        }

        choiceItemViewModel.setListItems(transmittedCheque?.products as MutableList<ChoiceItem>)
        if (isEmptyLastQuerySearch()) {
            choiceItemViewModel.setFilteredListItems(transmittedCheque?.products as MutableList<ChoiceItem>)
        }

        setupRecyclerViewListProducts()

        _binding.buttonNextToDistributionCheque.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelableArrayList(
                    "PRODUCT_TAG",
                    transmittedCheque?.products?.let { product ->
                        ArrayList<Parcelable>(product)
                    }
                )
            }

            Navigation.findNavController(_binding.root).navigate(
                R.id.action_choiceProductFragment_to_distributedProductsChequeFragments, bundle
            )
        }
    }


    override fun filterSearchingItems(query: String) {
        choiceItemViewModel.setFilteredListItems(
            choiceItemViewModel.listItems.value.filter { item ->
                val firstChequeTittle = item.getTitleItem().lowercase().trim()
                firstChequeTittle.contains(query.lowercase().trim())
            }.toMutableList()
        )

        recyclerViewListProductsAdapter.submitList(choiceItemViewModel.filteredListItems.value as List<Product>)
    }

    private fun setupRecyclerViewListProducts() = with(_binding) {
        recyclerViewListProductsAdapter = ListProductsAdapter()
        listProducts.layoutManager = LinearLayoutManager(requireContext())
        listProducts.adapter = recyclerViewListProductsAdapter
        recyclerViewListProductsAdapter.submitList(choiceItemViewModel.filteredListItems.value as List<Product>)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("CHEQUE_TAG_2", transmittedCheque)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        transmittedCheque = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("CHEQUE_TAG_2", Cheque::class.java) ?: transmittedCheque
        } else {
            arguments?.getParcelable("CHEQUE_TAG_2") ?: transmittedCheque
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}