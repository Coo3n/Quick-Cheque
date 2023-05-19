package com.example.quick_cheque.presentation.events

sealed class CreateRoomEvent {
    data class TitleRoomChanged(val title: String) : CreateRoomEvent()
    object CreateRoomSubmit : CreateRoomEvent()
}
