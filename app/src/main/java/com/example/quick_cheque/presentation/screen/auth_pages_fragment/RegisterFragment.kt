package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentRegisterBinding
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.example.quick_cheque.presentation.screen.viewmodels.RegisterFormEvent
import com.example.quick_cheque.presentation.screen.viewmodels.RegisterViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class RegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private val disposeBag = CompositeDisposable()

    companion object {
        enum class RegisterEvent {
            EMAIL_CHANGED,
            PASSWORD_CHANGED,
            REPEATED_PASSWORD_CHANGED
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.registerBtn.setOnClickListener {
            registerViewModel.onEvent(RegisterFormEvent.Submit)

            if (!registerViewModel.hasErrorInput()) {
                findNavController().navigate(
                    R.id.action_registerFragment_to_mainScreenFragment
                )
            }

            updateErrorMessage(binding.emailError, registerViewModel.state.emailError)
            updateErrorMessage(binding.passwordError, registerViewModel.state.passwordError)
            updateErrorMessage(
                binding.repeatedPasswordError,
                registerViewModel.state.repeatedPasswordError
            )
        }

        binding.switchButtonLogin.setOnClickListener {
//            findNavController().navigate(
//                R.id.action_registerFragment_to_loginFragment
//            )
        }

        binding.fragmentRegisterEmailField.editText?.textChanges(
            typeEvent = RegisterEvent.EMAIL_CHANGED
        )?.addTo(disposeBag)

        binding.fragmentRegisterPassword1Field.editText?.textChanges(
            typeEvent = RegisterEvent.PASSWORD_CHANGED
        )?.addTo(disposeBag)

        binding.fragmentRegisterPassword2Field.editText?.textChanges(
            typeEvent = RegisterEvent.REPEATED_PASSWORD_CHANGED
        )?.addTo(disposeBag)
    }

    private fun updateErrorMessage(errorView: TextView, error: String?) {
        errorView.visibility = if (error != null) View.VISIBLE else View.INVISIBLE
        errorView.text = error
    }

    private fun EditText.textChanges(typeEvent: RegisterEvent): Disposable {
        return RxTextView.textChanges(this)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ text ->
                when (typeEvent) {
                    RegisterEvent.EMAIL_CHANGED -> {
                        registerViewModel.onEvent(RegisterFormEvent.EmailOnChanged(text.toString()))
                    }
                    RegisterEvent.PASSWORD_CHANGED -> {
                        registerViewModel.onEvent(RegisterFormEvent.PasswordOnChanged(text.toString()))
                    }
                    RegisterEvent.REPEATED_PASSWORD_CHANGED -> {
                        registerViewModel.onEvent(RegisterFormEvent.RepeatedPasswordChanged(text.toString()))
                    }
                }

            }, { error ->
                Log.e("MyTag", "Error: $error")
            })
    }

    private fun Disposable.addTo(disposeBag: CompositeDisposable): Disposable {
        disposeBag.add(this)
        return this
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }
}