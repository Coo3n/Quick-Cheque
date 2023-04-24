package com.example.quick_cheque.presentation.screen.payment_cheque_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentWaitPaymentBinding
import com.example.quick_cheque.presentation.screen.BaseFragment

class WaitPaymentFragment : BaseFragment() {
    private var binding: FragmentWaitPaymentBinding? = null
    private val _binding: FragmentWaitPaymentBinding
        get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaitPaymentBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_waitPaymentFragment_to_paymentChequeFragment)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
