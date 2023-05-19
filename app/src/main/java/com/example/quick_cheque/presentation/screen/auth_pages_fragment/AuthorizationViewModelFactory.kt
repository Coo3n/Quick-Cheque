package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quick_cheque.domain.repository.AuthenticationRepository
import com.example.quick_cheque.domain.use_case.ValidateLoginUseCase
import com.example.quick_cheque.domain.use_case.ValidatePasswordUseCase
import com.example.quick_cheque.domain.use_case.ValidateRepeatedPasswordUseCase
import com.example.quick_cheque.domain.use_case.ValidateUsername
import com.example.quick_cheque.presentation.screen.viewmodel.AuthorizationViewModel
import javax.inject.Inject

class AuthorizationViewModelFactory @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val validateUsername: ValidateUsername,
    private val validateEmail: ValidateLoginUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthorizationViewModel::class.java)) {
            return AuthorizationViewModel(
                authenticationRepository,
                validateUsername,
                validateEmail,
                validatePassword,
                validateRepeatedPassword,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}