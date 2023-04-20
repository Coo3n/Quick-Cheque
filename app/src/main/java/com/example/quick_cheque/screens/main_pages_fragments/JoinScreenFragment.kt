package com.example.quick_cheque.screens.main_pages_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentJoinScreenBinding
import com.example.quick_cheque.screens.BaseFragment

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