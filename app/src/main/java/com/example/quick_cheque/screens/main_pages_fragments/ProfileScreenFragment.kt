package com.example.quick_cheque.screens.main_pages_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentProfileScreenBinding
import com.example.quick_cheque.screens.BaseFragment

class ProfileScreenFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVisibleToolBar()
        setVisibleHomeButton(false)

        binding.fragmentProfileOptSettings.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreenFragment_to_fragmentSettings)
        }
    }
}