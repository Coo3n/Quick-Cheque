package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quick_cheque.domain.use_case.AuthUseCase
import com.example.quick_cheque.domain.use_case.ValidateLoginUseCase
import com.example.quick_cheque.domain.use_case.ValidatePasswordUseCase
import com.example.quick_cheque.domain.use_case.ValidateRepeatedPasswordUseCase
import javax.inject.Inject

class RegisterViewModelFactory @Inject constructor(
    private val validateEmail: ValidateLoginUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase,
    private val authUseCase: AuthUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(
                validateEmail,
                validatePassword,
                validateRepeatedPassword,
                authUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}