package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Build
import android.os.Bundle
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


class ChoiceProductFragment : Fragment() {
    private var binding: FragmentChoiceProductBinding? = null
    private val _binding: FragmentChoiceProductBinding
        get() = binding!!

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
        setupRecyclerViewListProducts()

        val transmittedCheque: Cheque? = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable("CHEQUE_TAG", Cheque::class.java)
        } else {
            arguments?.getParcelable("CHEQUE_TAG")
        }

        _binding.buttonBackToChoiceCheque.setOnClickListener {
            Navigation.findNavController(_binding.root)
                .navigate(R.id.action_blankFragment_to_choiceChequeFragment)
        }

        _binding.buttonNextToDistributionCheque.setOnClickListener {
            Navigation.findNavController(_binding.root).navigate(
                R.id.action_blankFragment_to_distributedChequeFragment
            )
        }

        recyclerViewListProductsAdapter.submitList(transmittedCheque?.products)
    }

    private fun setupRecyclerViewListProducts() = with(_binding) {
        recyclerViewListProductsAdapter = ListProductsAdapter()
        listProducts.layoutManager = LinearLayoutManager(requireContext())
        listProducts.adapter = recyclerViewListProductsAdapter
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}