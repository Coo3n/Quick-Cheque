package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.FragmentLoginBinding
import com.example.quick_cheque.presentation.screen.BaseFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginFragment : BaseFragment() {
    private var binding: FragmentLoginBinding? = null
    private val _binding: FragmentLoginBinding
        get() = binding!!

    @Inject
    lateinit var authorizationViewModelFactory: AuthorizationViewModelFactory
    private lateinit var authorizationViewModel: AuthorizationViewModel

    private val disposeBag = CompositeDisposable()

    companion object {
        enum class LoginEvent {
            EMAIL_CHANGED,
            PASSWORD_CHANGED,
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApp).appComponent.injectLoginFragment(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setVisibleToolBar(false)
        authorizationViewModel = ViewModelProvider(
            this,
            authorizationViewModelFactory
        )[AuthorizationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.switchButtonRegister.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_registerFragment
            )
        }

        _binding.loginBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_choiceRoomFragment
            )
//            authorizationViewModel.onEvent(AuthFormEvent.AuthorizationSubmit)
//            lifecycleScope.launchWhenStarted {
//                authorizationViewModel.validationEventChannel.collect { event ->
//                    when (event) {
//                        is ValidationEvent.Success -> {
//                            findNavController().navigate(
//                                R.id.action_loginFragment_to_choiceRoomFragment
//                            )
//                            Toast.makeText(
//                                requireContext(),
//                                "Добро пожаловать!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                        is ValidationEvent.Failure -> {
//                            Toast.makeText(
//                                requireContext(),
//                                "Попробуйте еще раз!",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
//            }
        }

        _binding.fragmentLoginPassword1Field.editText?.textChanges(
            typeEvent = LoginEvent.PASSWORD_CHANGED
        )?.addTo(disposeBag)

        _binding.fragmentLoginEmailField.editText?.textChanges(
            typeEvent = LoginEvent.EMAIL_CHANGED
        )?.addTo(disposeBag)
    }

    private fun EditText.textChanges(typeEvent: LoginEvent): Disposable {
        return RxTextView.textChanges(this)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ text ->
                when (typeEvent) {
                    LoginEvent.EMAIL_CHANGED -> {
                        authorizationViewModel.onEvent(AuthFormEvent.EmailOnChanged(text.toString()))
                    }
                    LoginEvent.PASSWORD_CHANGED -> {
                        authorizationViewModel.onEvent(AuthFormEvent.PasswordOnChanged(text.toString()))
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