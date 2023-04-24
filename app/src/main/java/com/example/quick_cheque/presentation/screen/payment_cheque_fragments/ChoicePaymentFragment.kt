package com.example.quick_cheque.presentation.screen.payment_cheque_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quick_cheque.databinding.FragmentPaymentChoiceBinding
import com.example.quick_cheque.presentation.screen.BaseFragment

class ChoicePaymentFragment : BaseFragment() {
    private var binding: FragmentPaymentChoiceBinding? = null
    private val _binding: FragmentPaymentChoiceBinding
        get() = binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentChoiceBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}