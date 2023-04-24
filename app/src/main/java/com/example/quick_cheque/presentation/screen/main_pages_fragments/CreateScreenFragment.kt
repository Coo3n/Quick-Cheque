package com.example.quick_cheque.presentation.screen.main_pages_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quick_cheque.databinding.FragmentCreateRoomBinding
import com.example.quick_cheque.presentation.screen.BaseFragment

class CreateScreenFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateRoomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateRoomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}