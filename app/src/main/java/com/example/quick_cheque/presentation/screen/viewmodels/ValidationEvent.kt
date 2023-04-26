package com.example.quick_cheque.presentation.screen.viewmodels

sealed class ValidationEvent {
    object Success : ValidationEvent()
}
