package com.example.quick_cheque.screens.auth_pages_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentRegisterBinding
import com.example.quick_cheque.screens.BaseFragment

class RegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentRegisterRegisterBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_mainScreenFragment
            )
        }

        binding.switchButtonLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment
            )
        }
    }
}