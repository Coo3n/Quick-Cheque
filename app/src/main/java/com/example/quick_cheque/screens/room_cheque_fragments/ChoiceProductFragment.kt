package com.example.quick_cheque.screens.room_cheque_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentChoiceProductBinding


class ChoiceProductFragment : Fragment() {
    private var binding: FragmentChoiceProductBinding? = null
    private val _binding: FragmentChoiceProductBinding
        get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChoiceProductBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.buttonBackToChoiceCheque.setOnClickListener {
            Navigation.findNavController(_binding.root)
                .navigate(R.id.action_blankFragment_to_choiceChequeFragment)
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}