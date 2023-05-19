package com.example.quick_cheque.presentation.events

sealed class AuthFormEvent {
    data class UsernameOnChanged(val username: String) : AuthFormEvent()
    data class EmailOnChanged(val email: String) : AuthFormEvent()
    data class PasswordOnChanged(val password: String) : AuthFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : AuthFormEvent()
    object RegistrationSubmit : AuthFormEvent()
    object AuthorizationSubmit : AuthFormEvent()
}
