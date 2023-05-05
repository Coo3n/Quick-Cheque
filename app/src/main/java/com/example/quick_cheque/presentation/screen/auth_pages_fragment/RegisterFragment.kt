package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentLoginBinding
import com.example.quick_cheque.databinding.FragmentRegisterBinding
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RegisterFragment : BaseFragment() {
    private var binding: FragmentRegisterBinding? = null
    private val _binding: FragmentRegisterBinding
        get() = binding!!

    @Inject
    lateinit var authorizationViewModelFactory: AuthorizationViewModelFactory
    private lateinit var authorizationViewModel: AuthorizationViewModel

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
        authorizationViewModel = ViewModelProvider(
            this,
            authorizationViewModelFactory
        )[AuthorizationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLastValuesInTextFields()

        _binding.registerBtn.setOnClickListener {
            authorizationViewModel.onEvent(AuthFormEvent.RegistrationSubmit)

            lifecycleScope.launchWhenStarted {
                authorizationViewModel.validationEventChannel.collect { event ->
                    when (event) {
                        is ValidationEvent.Success -> {
                            findNavController().navigate(
                                R.id.action_registerFragment_to_loginFragment
                            )

                            Toast.makeText(
                                requireContext(),
                                "Вы успешно зарегистрированы!",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                        is ValidationEvent.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                "Попробуйте другую почту!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        _binding.switchButtonLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_registerFragment_to_loginFragment
            )
        }

        _binding.fragmentRegisterEmailField.editText?.textChanges(
            typeEvent = RegisterEvent.EMAIL_CHANGED
        )?.addTo(disposeBag)

        _binding.fragmentRegisterPassword1Field.editText?.textChanges(
            typeEvent = RegisterEvent.PASSWORD_CHANGED
        )?.addTo(disposeBag)

        _binding.fragmentRegisterPassword2Field.editText?.textChanges(
            typeEvent = RegisterEvent.REPEATED_PASSWORD_CHANGED
        )?.addTo(disposeBag)
    }

    private fun initLastValuesInTextFields() = with(_binding) {
        fragmentRegisterEmailField.editText?.setText(authorizationViewModel.state.value.email)
        fragmentRegisterPassword1Field.editText?.setText(authorizationViewModel.state.value.password)
        fragmentRegisterPassword2Field.editText?.setText(authorizationViewModel.state.value.repeatedPassword)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authorizationViewModel.state.collect {
                    emailError.text = authorizationViewModel.state.value.emailError
                    passwordError.text = authorizationViewModel.state.value.passwordError
                    repeatedPasswordError.text =
                        authorizationViewModel.state.value.repeatedPasswordError
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
                        authorizationViewModel.onEvent(AuthFormEvent.EmailOnChanged(text.toString()))
                    }
                    RegisterEvent.PASSWORD_CHANGED -> {
                        authorizationViewModel.onEvent(AuthFormEvent.PasswordOnChanged(text.toString()))
                    }
                    RegisterEvent.REPEATED_PASSWORD_CHANGED -> {
                        authorizationViewModel.onEvent(AuthFormEvent.RepeatedPasswordChanged(text.toString()))
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
        binding = null
    }
}