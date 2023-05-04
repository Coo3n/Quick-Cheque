package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentRegisterBinding
import com.example.quick_cheque.di.AppComponent
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding

    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel

    private val disposeBag = CompositeDisposable()

    companion object {
        enum class RegisterEvent {
            EMAIL_CHANGED,
            PASSWORD_CHANGED,
            REPEATED_PASSWORD_CHANGED
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as MyApp).appComponent.injectRegisterFragment(this)
        registerViewModel = ViewModelProvider(
            this,
            registerViewModelFactory
        )[RegisterViewModel::class.java]
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
        initLastValuesInTextFields()

        binding.registerBtn.setOnClickListener {
            registerViewModel.onEvent(RegisterFormEvent.Submit)

            lifecycleScope.launchWhenStarted {
                registerViewModel.validationEventChannel.collect { event ->
                    when (event) {
                        is ValidationEvent.Success -> {
                            findNavController().navigate(
                                R.id.action_registerFragment_to_choiceRoomFragment
                            )
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()

                        }
                        is ValidationEvent.Failure -> {
                            Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.switchButtonLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment
            )
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

    private fun initLastValuesInTextFields() = with(binding) {
        fragmentRegisterEmailField.editText?.setText(registerViewModel.state.value.email)
        fragmentRegisterPassword1Field.editText?.setText(registerViewModel.state.value.password)
        fragmentRegisterPassword2Field.editText?.setText(registerViewModel.state.value.repeatedPassword)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.state.collect {
                    emailError.text = registerViewModel.state.value.emailError
                    passwordError.text = registerViewModel.state.value.passwordError
                    repeatedPasswordError.text =
                        registerViewModel.state.value.repeatedPasswordError
                }
            }
        }
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