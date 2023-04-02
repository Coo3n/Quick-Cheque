package com.example.quick_cheque.screens.payment_cheque_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentPaymentChoiceBinding

class ChoicePaymentFragment : Fragment() {
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

        _binding.buttonBack.setOnClickListener {
            // TODO
            //Navigation.findNavController(_binding.root)
            //    .navigate(R.id.action_choicePaymentFragment_pop)
        }

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}