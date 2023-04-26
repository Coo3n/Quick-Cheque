package com.example.quick_cheque.presentation.screen.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_cheque.domain.use_case.ValidateLogin
import com.example.quick_cheque.domain.use_case.ValidatePassword
import com.example.quick_cheque.domain.use_case.ValidateRepeatedPassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val validateEmail: ValidateLogin = ValidateLogin(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
) : ViewModel() {
    var state = RegisterState()

    private val _validationEventChannel = Channel<ValidationEvent>()
    val validationEventChannel = _validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegisterFormEvent) {
        when (event) {
            is RegisterFormEvent.EmailOnChanged -> {
                state = state.copy(email = event.email)
            }
            is RegisterFormEvent.PasswordOnChanged -> {
                state = state.copy(password = event.password)
            }
            is RegisterFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegisterFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun hasErrorInput(): Boolean {
        val email = validateEmail.execute(state.email)
        val password = validatePassword.execute(state.password)
        val repeatedPassword = validateRepeatedPassword.execute(
            state.password,
            state.repeatedPassword
        )

        return listOf(email, password, repeatedPassword).any { !it.successful }
    }

    private fun submitData() {
        val email = validateEmail.execute(state.email)
        val password = validatePassword.execute(state.password)
        val repeatedPassword = validateRepeatedPassword.execute(
            state.password,
            state.repeatedPassword
        )

        val hasError = listOf(email, password, repeatedPassword).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = email.errorMessage,
                passwordError = password.errorMessage,
                repeatedPasswordError = repeatedPassword.errorMessage
            )

            return
        }

        viewModelScope.launch {
            _validationEventChannel.send(ValidationEvent.Success)
        }
    }
}



