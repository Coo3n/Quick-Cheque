package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListProductsAdapter
import com.example.quick_cheque.databinding.FragmentChoiceProductBinding
import com.example.quick_cheque.model.Cheque
import com.example.quick_cheque.model.Product
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class ChoiceProductFragment : Fragment() {
    private var binding: FragmentChoiceProductBinding? = null
    private val _binding: FragmentChoiceProductBinding
        get() = binding!!

    private val disposeBag = CompositeDisposable()
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

        transmittedCheque = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("CHEQUE_TAG", Cheque::class.java) ?: transmittedCheque
        } else {
            arguments?.getParcelable("CHEQUE_TAG") ?: transmittedCheque
        }

        setupRecyclerViewListProducts(transmittedCheque)

        _binding.buttonBackToChoiceCheque.setOnClickListener {
            Navigation.findNavController(_binding.root)
                .navigate(R.id.action_choiceProductFragment_to_choiceChequeFragment)
        }

        disposeBag.add(
            RxTextView.textChanges(_binding.searchEditTextInProducts)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    filterSearchingItems(it.toString())
                }
        )

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

    private fun filterSearchingItems(searchText: String) {
        val filteredListItems: MutableList<Product> =
            transmittedCheque?.products?.filter { item ->
                val firstChequeTittle = item.titleProduct.lowercase().trim()
                firstChequeTittle.contains(searchText.lowercase().trim())
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
        disposeBag.clear()
        super.onDestroy()
    }
}