package com.example.quick_cheque.presentation.screen.auth_pages_fragment

sealed class RegisterFormEvent {
    data class EmailOnChanged(val email: String) : RegisterFormEvent()
    data class PasswordOnChanged(val password: String) : RegisterFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : RegisterFormEvent()
    object Submit : RegisterFormEvent()
}

