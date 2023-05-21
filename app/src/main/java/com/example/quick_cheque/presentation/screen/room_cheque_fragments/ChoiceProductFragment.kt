package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.data.repository.ProductRepositoryImpl
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.presentation.adapter.ListProductsAdapter
import com.example.quick_cheque.databinding.FragmentChoiceProductBinding
import com.example.quick_cheque.domain.model.Cheque
import com.example.quick_cheque.domain.model.Product
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.example.quick_cheque.presentation.screen.viewmodel.ChoiceProductViewModel
import javax.inject.Inject


class ChoiceProductFragment : BaseFragment() {
    private var binding: FragmentChoiceProductBinding? = null
    private val _binding: FragmentChoiceProductBinding
        get() = binding!!

    private lateinit var choiceProductViewModel: ChoiceProductViewModel

    private var transmittedCheque: Cheque? = null
    private lateinit var recyclerViewListProductsAdapter: ListProductsAdapter

    @Inject
    lateinit var productRepositoryImpl: ProductRepositoryImpl

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.injectChoiceProductFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ChoiceProductViewModel.ChoiceItemViewModelFactory(productRepositoryImpl)
        choiceProductViewModel = ViewModelProvider(
            this,
            factory
        )[ChoiceProductViewModel::class.java]
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

//        transmittedCheque = if (Build.VERSION.SDK_INT >= 33) {
//            arguments?.getParcelable("CHEQUE_TAG", Cheque::class.java) ?: transmittedCheque
//        } else {
//            arguments?.getParcelable("CHEQUE_TAG") ?: transmittedCheque
//        }

//        transmittedCheque?.products?.let { choiceProductViewModel.setListItems(it) }
//        if (isEmptyLastQuerySearch()) {
//            transmittedCheque?.products?.let { choiceProductViewModel.setFilteredListItems(it) }
//        }

        setupRecyclerViewListProducts()

        _binding.buttonNextToDistributionCheque.setOnClickListener {
            val bundle = Bundle().apply {
//                putParcelableArrayList(
//                    "PRODUCT_TAG",
////                    transmittedCheque?.products?.let { product ->
////                        ArrayList<Parcelable>(product)
////                    }
//                )
            }

            Navigation.findNavController(_binding.root).navigate(
                R.id.action_choiceProductFragment_to_distributedProductsChequeFragments, bundle
            )
        }
    }


    override fun filterSearchingItems(query: String) {
        choiceProductViewModel.setFilteredListItems(
            choiceProductViewModel.listItems.value.filter { item ->
                val firstChequeTittle = item.getTitleItem().lowercase().trim()
                firstChequeTittle.contains(query.lowercase().trim())
            }.toMutableList()
        )

        recyclerViewListProductsAdapter.submitList(choiceProductViewModel.filteredListItems.value as List<Product>)
    }

    private fun setupRecyclerViewListProducts() = with(_binding) {
        recyclerViewListProductsAdapter = ListProductsAdapter()
        listProducts.layoutManager = LinearLayoutManager(requireContext())
        listProducts.adapter = recyclerViewListProductsAdapter
        recyclerViewListProductsAdapter.submitList(choiceProductViewModel.filteredListItems.value as List<Product>)
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