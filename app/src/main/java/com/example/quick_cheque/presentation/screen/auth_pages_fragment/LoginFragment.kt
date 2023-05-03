package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentLoginBinding
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class LoginFragment : BaseFragment() {
    private lateinit var binding: FragmentLoginBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_choiceRoomFragment
            )
        }

        binding.switchButtonRegister.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }

        binding.fragmentLoginEmailField.editText?.let {
            RxTextView.textChanges(it)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ text ->
                    val processedText = text.trim().toString().lowercase()
                    registerViewModel.onEvent(RegisterFormEvent.EmailOnChanged(processedText))
                }, {
                    Log.i("MyTag", "fragmentLoginEmailField Error")
                })
        }


    }
}