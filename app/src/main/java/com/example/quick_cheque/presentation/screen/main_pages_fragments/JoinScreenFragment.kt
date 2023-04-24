package com.example.quick_cheque.presentation.screen.main_pages_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quick_cheque.databinding.FragmentJoinScreenBinding
import com.example.quick_cheque.presentation.screen.BaseFragment

class JoinScreenFragment : BaseFragment() {
    private lateinit var binding: FragmentJoinScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setVisibleToolBar()
        setVisibleHomeButton(false)
        binding = FragmentJoinScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}