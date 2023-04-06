package com.example.quick_cheque.screens.payment_cheque_fragments

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListProductsAdapter
import com.example.quick_cheque.databinding.FragmentPaymentChequeBinding
import com.example.quick_cheque.model.Product
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

class PaymentChequeFragment : Fragment() {
    private var binding: FragmentPaymentChequeBinding? = null
    private val _binding: FragmentPaymentChequeBinding
        get() = binding!!

    private lateinit var recyclerViewListProductsAdapter: ListProductsAdapter
    private lateinit var listItems: MutableList<Product>
    private lateinit var sumOfCheque : BigDecimal

    private val disposeBag = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentChequeBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listItems = getProductList()
        setupRecyclerViewListProducts()
        recyclerViewListProductsAdapter.submitList(listItems)

        disposeBag.add(
            RxTextView.textChanges(_binding.searchField)
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.i("MyTag", it.toString())
                    filterSearchingItems(it.toString())
                }
        )

        sumOfCheque = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listItems.stream()
                .map { p -> p.price }
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        } else {
            TODO("VERSION.SDK_INT < N")
        }

        if (sumOfCheque.equals(1)) {
            _binding.buttonPay.text = getString(R.string.payment_one_ruble).replace("%", sumOfCheque.toString())
        } else {
            _binding.buttonPay.text = getString(R.string.payment_many_rubles).replace("%", sumOfCheque.toString())
        }
        _binding.buttonPay.setOnClickListener {
            showBottomSheetDialog();
        }
        _binding.buttonBack.setOnClickListener {
            Navigation.findNavController(_binding.root)
                .navigate(R.id.action_PaymentChequeFragment_pop)
        }
    }

    private fun showBottomSheetDialog() {
        var bottomSheetDialog = this.context?.let { BottomSheetDialog(it, R.style.bottom_sheet_dialog_theme) };
        bottomSheetDialog?.setContentView(R.layout.fragment_payment_choice);

        if (sumOfCheque.equals(1)) {
            _binding.buttonPay.text = getString(R.string.payment_one_ruble).replace("%", sumOfCheque.toString())
        } else {
            _binding.buttonPay.text = getString(R.string.payment_many_rubles).replace("%", sumOfCheque.toString())
        }
        if (bottomSheetDialog != null) {
            if (sumOfCheque.equals(1)) {
                bottomSheetDialog.findViewById<Button>(R.id.buttonPay)?.text = getString(R.string.payment_one_ruble).replace("%", sumOfCheque.toString())
            } else {
                bottomSheetDialog.findViewById<Button>(R.id.buttonPay)?.text = getString(R.string.payment_many_rubles).replace("%", sumOfCheque.toString())
            }
        }

        bottomSheetDialog?.show();

    }
    private fun filterSearchingItems(searchText: String) {
        val filteredListItems: MutableList<Product> =
            (listItems as MutableList<Product>)
                .filter { item ->
                    item.titleProduct.lowercase().trim()
                        .contains(searchText.lowercase().trim())
                }
                .toMutableList()

        recyclerViewListProductsAdapter.submitList(filteredListItems)
    }

    private fun setupRecyclerViewListProducts() = with(_binding) {
        recyclerViewListProductsAdapter = object : ListProductsAdapter() {
            @Override
            override fun onBindViewHolder(holder: ListProductsViewHolder, position: Int) {
                holder.bind(getItem(position))
                holder.itemView.findViewById<ImageView>(R.id.divideLine).visibility = View.INVISIBLE
                holder.itemView.findViewById<ConstraintLayout>(R.id.userInfoContainer).visibility =
                    View.GONE
            }
        }

        listProducts.layoutManager = LinearLayoutManager(requireContext())
        listProducts.adapter = recyclerViewListProductsAdapter
    }

    private fun getProductList(): MutableList<Product> {
        return mutableListOf(
            Product(
                "Газировка",
                BigDecimal(30),
                3
            ),
            Product(
                "Чипсы",
                BigDecimal(100),
                9
            ),
            Product(
                "Кола",
                BigDecimal(5),
                1
            )
        )
    }

    override fun onDestroy() {
        disposeBag.clear()
        binding = null
        super.onDestroy()
    }
}
