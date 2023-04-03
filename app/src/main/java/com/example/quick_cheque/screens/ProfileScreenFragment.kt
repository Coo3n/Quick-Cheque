package com.example.quick_cheque.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment() {
    private lateinit var binding: FragmentProfileScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentProfileOptSettings.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreenFragment_to_fragmentSettings)
        }
    }
}