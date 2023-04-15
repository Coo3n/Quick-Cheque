package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListProductsAdapter
import com.example.quick_cheque.databinding.FragmentChoiceProductBinding
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.Product
import com.example.quick_cheque.screens.BaseFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class ChoiceProductFragment : BaseFragment() {
    private var binding: FragmentChoiceProductBinding? = null
    private val _binding: FragmentChoiceProductBinding
        get() = binding!!

    private var transmittedCheque: Cheque? = null
    private lateinit var recyclerViewListProductsAdapter: ListProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChoiceProductBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val toolbar = updateToolbar(
//            text = "Чек",
//            menu = R.menu.menu_with_search,
//        )
//
//        toolbar.apply {
//            setNavigationOnClickListener {
//                findNavController().navigate(R.id.action_choiceProductFragment_to_choiceChequeFragment)
//            }
//
//            setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.add_button -> {
//                        Toast.makeText(requireContext(), "Добавить", Toast.LENGTH_SHORT).show()
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

        transmittedCheque = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("CHEQUE_TAG", Cheque::class.java) ?: transmittedCheque
        } else {
            arguments?.getParcelable("CHEQUE_TAG") ?: transmittedCheque
        }

        setupRecyclerViewListProducts(transmittedCheque)

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
        val filteredListItems: MutableList<Product> =
            transmittedCheque?.products?.filter { item ->
                val firstChequeTittle = item.titleProduct.lowercase().trim()
                firstChequeTittle.contains(query.lowercase().trim())
            }?.toMutableList() ?: mutableListOf()

        recyclerViewListProductsAdapter.submitList(filteredListItems)
    }

    private fun setupRecyclerViewListProducts(transmittedCheque: Cheque?) = with(_binding) {
        recyclerViewListProductsAdapter = ListProductsAdapter()
        listProducts.layoutManager = LinearLayoutManager(requireContext())
        listProducts.adapter = recyclerViewListProductsAdapter
        recyclerViewListProductsAdapter.submitList(transmittedCheque?.products)
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