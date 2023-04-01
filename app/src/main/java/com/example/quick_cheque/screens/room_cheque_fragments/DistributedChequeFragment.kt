package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quick_cheque.adapters.ListDistributedProductsAdapter
import com.example.quick_cheque.databinding.FragmentDistributedChequeBinding

class DistributedChequeFragment : Fragment() {
    private var binding: FragmentDistributedChequeBinding? = null
    private val _binding: FragmentDistributedChequeBinding
        get() = binding!!

    private lateinit var distributionProductListAdapter: ListDistributedProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistributedChequeBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerDistributionProductList()
    }

    private fun setupRecyclerDistributionProductList() = with(_binding) {
        distributionProductListAdapter = ListDistributedProductsAdapter()
        listDistributionProducts.layoutManager = LinearLayoutManager(requireContext())
        listDistributionProducts.adapter = distributionProductListAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}