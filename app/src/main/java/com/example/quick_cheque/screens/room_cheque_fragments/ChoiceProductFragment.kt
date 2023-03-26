package com.example.quick_cheque.screens.room_cheque_fragments

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
import com.example.quick_cheque.model.Product
import java.math.BigDecimal


class ChoiceProductFragment : Fragment() {
    private var binding: FragmentChoiceProductBinding? = null
    private val _binding: FragmentChoiceProductBinding
        get() = binding!!

    private lateinit var recyclerViewListProductsAdapter: ListProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChoiceProductBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewListProducts()

        _binding.buttonBackToChoiceCheque.setOnClickListener {
            Navigation.findNavController(_binding.root)
                .navigate(R.id.action_blankFragment_to_choiceChequeFragment)
        }

        recyclerViewListProductsAdapter.submitList(
            mutableListOf(
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
        )
    }

    private fun setupRecyclerViewListProducts() = with(_binding) {
        recyclerViewListProductsAdapter = ListProductsAdapter()
        listProducts.setHasFixedSize(true)
        listProducts.layoutManager = LinearLayoutManager(requireContext())
        listProducts.adapter = recyclerViewListProductsAdapter
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}