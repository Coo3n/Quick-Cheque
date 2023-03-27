package com.example.quick_cheque.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.MainActivity
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentChoiceChequeBinding
import com.example.quick_cheque.databinding.FragmentMainScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainScreenFragment : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rectangle1.setOnClickListener {
           // findNavController().navigate(R.id.action_mainScreenFragment_to_choiceChequeFragment)
        }

        activity?.parent?.findViewById<BottomNavigationView>(R.id.mainBottomNav)?.visibility = View.INVISIBLE
    }
}