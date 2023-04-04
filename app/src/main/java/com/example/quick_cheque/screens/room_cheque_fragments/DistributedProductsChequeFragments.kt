package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_cheque.R
import com.example.quick_cheque.adapters.ListDistributedProductsAdapter
import com.example.quick_cheque.databinding.FragmentDistributedProductsChequeFragmentsBinding
import com.example.quick_cheque.model.Product
import com.example.quick_cheque.screens.BaseFragment

class DistributedProductsChequeFragments : BaseFragment() {
    private var binding: FragmentDistributedProductsChequeFragmentsBinding? = null
    private val _binding: FragmentDistributedProductsChequeFragmentsBinding
        get() = binding!!

    private var transmittedListProduct: MutableList<Product>? = null
    private lateinit var distributionProductListAdapter: ListDistributedProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistributedProductsChequeFragmentsBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = updateToolbar("Чек")
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_distributedProductsChequeFragments_to_choiceProductFragment)
        }

        transmittedListProduct = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelableArrayList("PRODUCT_TAG", Product::class.java)
        } else {
            arguments?.getParcelableArrayList("PRODUCT_TAG")
        }

        setupRecyclerDistributionProductList(transmittedListProduct)

        _binding.buttonOmpleteHeque.setOnClickListener {
            findNavController().navigate(
                R.id.action_distributedProductsChequeFragments_to_choicePaymentFragment
            )
        }
    }


    private fun setupRecyclerDistributionProductList(transmittedCheque: MutableList<Product>?) =
        with(_binding) {
            distributionProductListAdapter = ListDistributedProductsAdapter()
            listDistributionProducts.layoutManager = LinearLayoutManager(requireContext())
            listDistributionProducts.adapter = distributionProductListAdapter
            distributionProductListAdapter.submitList(transmittedCheque)
        }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}