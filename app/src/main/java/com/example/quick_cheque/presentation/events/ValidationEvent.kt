package com.example.quick_cheque.presentation.events

sealed class ValidationEvent {
    object Success : ValidationEvent()
    object Failure : ValidationEvent()
}
