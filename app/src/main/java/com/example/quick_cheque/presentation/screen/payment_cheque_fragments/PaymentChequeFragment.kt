package com.example.quick_cheque.presentation.screen.payment_cheque_fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_cheque.R
import com.example.quick_cheque.presentation.adapter.ListProductsAdapter
import com.example.quick_cheque.databinding.FragmentPaymentChequeBinding
import com.example.quick_cheque.domain.model.Product
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.math.BigDecimal

class PaymentChequeFragment : BaseFragment() {
    private var binding: FragmentPaymentChequeBinding? = null
    private val _binding: FragmentPaymentChequeBinding
        get() = binding!!

    private lateinit var recyclerViewListProductsAdapter: ListProductsAdapter
    private lateinit var listItems: MutableList<Product>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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

//        val toolbar = updateToolbar(
//            text = getString(R.string.payment),
//            menu = R.menu.menu_with_search,
//        )
//
//        toolbar.apply {
//            setNavigationOnClickListener {
//                findNavController().navigate(R.id.action_paymentChequeFragment_to_waitPaymentFragment)
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

        val sumOfCheque = listItems.stream()
            .map { p -> p.price }
            .reduce(BigDecimal.ZERO, BigDecimal::add)

        _binding.chequeSum.text =
            getString(R.string.payment_sum).replace("%", sumOfCheque.toString())

        _binding.buttonPay.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog =
            this.context?.let { BottomSheetDialog(it, R.style.bottom_sheet_dialog_theme) }
        bottomSheetDialog?.setContentView(R.layout.fragment_payment_choice)
        bottomSheetDialog?.behavior?.state = BottomSheetBehavior.STATE_EXPANDED

        bottomSheetDialog?.findViewById<ImageButton>(R.id.buttonPaySPB)?.setOnClickListener {
            Toast.makeText(this.context, "Paid by SPB", Toast.LENGTH_LONG).show()
            showCompletedChequeCard()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog?.findViewById<ImageButton>(R.id.buttonPayUmoney)?.setOnClickListener {
            Toast.makeText(this.context, "Paid by Umoney", Toast.LENGTH_LONG).show()
            showCompletedChequeCard()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog?.findViewById<ImageButton>(R.id.buttonPayQiwi)?.setOnClickListener {
            Toast.makeText(this.context, "Paid by Qiwi", Toast.LENGTH_LONG).show()
            showCompletedChequeCard()
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog?.show()
    }

    private fun showCompletedChequeCard() {
        val alertDialog = this.context?.let { Dialog(it, R.style.bottom_sheet_dialog_theme) }
        alertDialog?.setContentView(R.layout.fragment_payment_complete)
        alertDialog?.show()
    }


    override fun filterSearchingItems(query: String) {
        val filteredListItems: MutableList<Product> =
            (listItems as MutableList<Product>)
                .filter { item ->
                    item.titleProduct.lowercase().trim()
                        .contains(query.lowercase().trim())
                }
                .toMutableList()

        recyclerViewListProductsAdapter.submitList(filteredListItems)
    }

    private fun setupRecyclerViewListProducts() = with(_binding) {
        recyclerViewListProductsAdapter = object : ListProductsAdapter() {
            @Override
            override fun onBindViewHolder(holder: ListProductsViewHolder, position: Int) {
                holder.bind(getItem(position))
                holder.itemView.findViewById<ImageView>(R.id.divider_line).visibility =
                    View.INVISIBLE
                holder.itemView.findViewById<ConstraintLayout>(R.id.user_info_container).visibility =
                    View.GONE
            }
        }

        listProducts.layoutManager = LinearLayoutManager(requireContext())
        listProducts.adapter = recyclerViewListProductsAdapter
    }

    private fun getProductList(): MutableList<Product> {
        return mutableListOf(
            Product(
                id = 1,
                "Газировка",
                BigDecimal(30),
                3
            ),
            Product(
                id = 2,
                "Чипсы",
                BigDecimal(100),
                9
            ),
            Product(
                id = 3,
                "Кола",
                BigDecimal(5),
                1
            )
        )
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
