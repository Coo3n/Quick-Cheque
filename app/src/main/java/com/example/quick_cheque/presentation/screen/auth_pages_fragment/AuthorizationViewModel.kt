package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.domain.repository.AuthenticationRepository
import com.example.quick_cheque.domain.use_case.ValidateLoginUseCase
import com.example.quick_cheque.domain.use_case.ValidatePasswordUseCase
import com.example.quick_cheque.domain.use_case.ValidateRepeatedPasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val validateEmail: ValidateLoginUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase,
) : ViewModel() {
    private val _validationEventChannel = Channel<ValidationEvent>()
    val validationEventChannel = _validationEventChannel.receiveAsFlow()

    private var _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onEvent(event: AuthFormEvent) {
        when (event) {
            is AuthFormEvent.EmailOnChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is AuthFormEvent.PasswordOnChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is AuthFormEvent.RepeatedPasswordChanged -> {
                _state.value = _state.value.copy(repeatedPassword = event.repeatedPassword)
            }
            is AuthFormEvent.RegistrationSubmit -> {
                registrationSubmit()
            }
            is AuthFormEvent.AuthorizationSubmit -> {
                authorizationSubmit()
            }
        }
    }

    private suspend fun authorization(): Boolean {
        val result = authenticationRepository.authenticate(
            AuthenticationRequestDto(
                email = _state.value.email,
                password = _state.value.password,
            )
        ).first()

        if (result.status == "failure") {
            _state.value = _state.value.copy(
                emailError = result.message
            )
            return false
        }

        return true
    }

    private fun authorizationSubmit() {
        viewModelScope.launch {
            val success = withContext(Dispatchers.IO) {
                authorization()
            }

            _validationEventChannel.send(
                if (success) {
                    ValidationEvent.Success
                } else {
                    ValidationEvent.Failure
                }
            )
        }
    }


    private suspend fun register(): Boolean {
        val result = authenticationRepository.register(
            AuthenticationRequestDto(
                email = _state.value.email,
                username = "ilya",
                password = _state.value.password,
            )
        ).first()

        if (result.status == "failure") {
            _state.value = _state.value.copy(
                emailError = result.message
            )
            return false
        }

        return true
    }

    private fun registrationSubmit() {
        viewModelScope.launch {
            val email = validateEmail.execute(_state.value.email)
            val password = validatePassword.execute(_state.value.password)
            val repeatedPassword = validateRepeatedPassword.execute(
                _state.value.password,
                _state.value.repeatedPassword
            )

            val hasError = listOf(email, password, repeatedPassword).any { !it.successful }

            _state.value = _state.value.copy(
                emailError = email.errorMessage,
                passwordError = password.errorMessage,
                repeatedPasswordError = repeatedPassword.errorMessage
            )

            if (!hasError) {
                val success = withContext(Dispatchers.IO) {
                    register()
                }

                _validationEventChannel.send(
                    if (success) {
                        ValidationEvent.Success
                    } else {
                        ValidationEvent.Failure
                    }
                )
            }
        }
    }
}