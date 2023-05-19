package com.example.quick_cheque.presentation.events

sealed class ChoiceItemEvent {
    object Refresh : ChoiceItemEvent()
}