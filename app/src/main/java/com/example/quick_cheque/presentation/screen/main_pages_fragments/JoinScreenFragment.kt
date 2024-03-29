package com.example.quick_cheque.presentation.screen.main_pages_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentJoinScreenBinding
import com.example.quick_cheque.presentation.screen.BaseFragment

class JoinScreenFragment : BaseFragment() {
    private lateinit var binding: FragmentJoinScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.joinButton.setOnClickListener {
            findNavController().navigate(R.id.action_joinScreenFragment_to_choiceChequeFragment)
        }
    }
}