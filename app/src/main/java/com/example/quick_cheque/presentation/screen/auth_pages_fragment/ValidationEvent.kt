package com.example.quick_cheque.presentation.screen.auth_pages_fragment

sealed class ValidationEvent {
    object Success : ValidationEvent()
    object Failure : ValidationEvent()
}
