package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import android.util.Log
import androidx.lifecycle.*
import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.domain.use_case.AuthUseCase
import com.example.quick_cheque.domain.use_case.ValidateLoginUseCase
import com.example.quick_cheque.domain.use_case.ValidatePasswordUseCase
import com.example.quick_cheque.domain.use_case.ValidateRepeatedPasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val validateEmail: ValidateLoginUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase,
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _validationEventChannel = Channel<ValidationEvent>()
    val validationEventChannel = _validationEventChannel.receiveAsFlow()

    private var _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.EmailOnChanged -> {
                _state.value = _state.value.copy(email = event.email)
            }
            is RegisterFormEvent.PasswordOnChanged -> {
                _state.value = _state.value.copy(password = event.password)
            }
            is RegisterFormEvent.RepeatedPasswordChanged -> {
                _state.value = _state.value.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegisterFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private suspend fun register(passwordError: String?, repeatedPasswordError: String?): Boolean {
        val result = authUseCase.registration(
            AuthenticationRequestDto(
                email = _state.value.email,
                username = "ilya",
                password = _state.value.password,
            )
        ).first()

        return if (result.status == "failure") {
            _state.value = _state.value.copy(
                emailError = result.message,
                passwordError = passwordError,
                repeatedPasswordError = repeatedPasswordError
            )
            false
        } else {
            _state.value = _state.value.copy(
                emailError = null,
                passwordError = null,
                repeatedPasswordError = null
            )
            true
        }
    }

    private fun submitData() {
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
                    register(password.errorMessage, repeatedPassword.errorMessage)
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