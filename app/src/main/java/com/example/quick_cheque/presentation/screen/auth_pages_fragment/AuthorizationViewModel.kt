package com.example.quick_cheque.presentation.screen.auth_pages_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_cheque.data.remote.dto.AuthenticationRequestDto
import com.example.quick_cheque.domain.repository.AuthenticationRepository
import com.example.quick_cheque.domain.use_case.ValidateLoginUseCase
import com.example.quick_cheque.domain.use_case.ValidatePasswordUseCase
import com.example.quick_cheque.domain.use_case.ValidateRepeatedPasswordUseCase
import com.example.quick_cheque.domain.use_case.ValidateUsername
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val validateUsername: ValidateUsername,
    private val validateEmail: ValidateLoginUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val validateRepeatedPassword: ValidateRepeatedPasswordUseCase,
) : ViewModel() {
    private val _validationEventChannel = Channel<ValidationEvent>()
    val validationEventChannel = _validationEventChannel.receiveAsFlow()

    private var _state = MutableStateFlow(AuthorizationState())
    val state = _state.asStateFlow()

    private var job: Job? = null // Ссылка на Job для отмены подписки

    fun onEvent(event: AuthFormEvent) {
        when (event) {
            is AuthFormEvent.UsernameOnChanged -> {
                _state.value = _state.value.copy(username = event.username)
            }
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
        var isSuccess = false

        job = authenticationRepository.authenticate(
            AuthenticationRequestDto(
                email = _state.value.email,
                password = _state.value.password,
            )
        ).onEach { response ->
            when (response) {
                is Resource.Success -> {
                    isSuccess = true
                    job?.cancel()
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        emailError = response.message
                    )
                    isSuccess = false
                    job?.cancel()
                }
                is Resource.Loading -> TODO()
            }
        }.launchIn(viewModelScope)

        job?.join()

        return isSuccess
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
        var isSuccess = false

        job = authenticationRepository.register(
            AuthenticationRequestDto(
                username = _state.value.username,
                email = _state.value.email,
                password = _state.value.password,
            )
        ).onEach { response ->
            when (response) {
                is Resource.Success -> {
                    isSuccess = true
                    job?.cancel()
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        emailError = response.message
                    )
                    isSuccess = false
                    job?.cancel()
                }
                is Resource.Loading -> TODO()
            }
        }.launchIn(viewModelScope)

        job?.join()

        return isSuccess
    }

    private fun registrationSubmit() {
        viewModelScope.launch {
            val username = validateUsername.execute(_state.value.username)
            val email = validateEmail.execute(_state.value.email)
            val password = validatePassword.execute(_state.value.password)
            val repeatedPassword = validateRepeatedPassword.execute(
                _state.value.password,
                _state.value.repeatedPassword
            )

            val hasError = listOf(
                username,
                email,
                password,
                repeatedPassword
            ).any { !it.successful }

            _state.value = _state.value.copy(
                usernameError = username.errorMessage,
                emailError = email.errorMessage,
                passwordError = password.errorMessage,
                repeatedPasswordError = repeatedPassword.errorMessage,
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