package com.example.quick_cheque.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.adapters.ListAdapterWithDelegates
import com.example.quick_cheque.databinding.FragmentPaymentWindowBinding
import com.example.quick_cheque.delegates.ProductListDelegate
import com.example.quick_cheque.list_items.ProductListItem
import com.example.quick_cheque.model.Product
import java.math.BigDecimal

class PaymentWindowFragment : Fragment() {
    private var binding: FragmentPaymentWindowBinding? = null
    private val _binding: FragmentPaymentWindowBinding
        get() = binding!!

    private lateinit var paymentRecyclerViewList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentWindowBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentRecyclerViewList = _binding.productList
        paymentRecyclerViewList.layoutManager = LinearLayoutManager(requireContext())

        paymentRecyclerViewList.adapter = ListAdapterWithDelegates(
            delegates = listOf(ProductListDelegate()),
            listItems = getProductList()
        )
    }

    private fun getProductList(): List<ProductListItem> {
        return listOf(
            ProductListItem(
                Product(
                    name = "Cookies",
                    price = BigDecimal("79.99"),
                    count = 1,
                ),
            ),

            ProductListItem(
                Product(
                    name = "Tea",
                    price = BigDecimal("129.99"),
                    count = 2,
                ),
            ),
        )
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
