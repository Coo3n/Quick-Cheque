package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentLoginBinding
import com.example.quick_cheque.presentation.screen.BaseFragment

class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentLoginLoginBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_mainScreenFragment
            )
        }

        binding.switchButtonRegister.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }
    }
}