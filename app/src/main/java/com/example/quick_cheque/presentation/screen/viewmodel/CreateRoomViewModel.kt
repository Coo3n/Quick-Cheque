package com.example.quick_cheque.presentation.screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quick_cheque.data.remote.dto.InsertedRoomDto
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.presentation.events.CreateRoomEvent
import com.example.quick_cheque.presentation.events.ValidationEvent
import com.example.quick_cheque.presentation.screen.room_cheque_fragments.CreatingRoomState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CreateRoomViewModel(
    private val roomRepository: RoomRepositoryImpl
) : ViewModel() {
    private val _validationEventChannel = Channel<ValidationEvent>()
    val validationEventChannel = _validationEventChannel.receiveAsFlow()

    private val _creatingRoomState = MutableStateFlow(CreatingRoomState())
    val creatingRoomState = _creatingRoomState.asStateFlow()

    class CreateRoomViewModelFactory(
        private val roomRepository: RoomRepositoryImpl
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            when (modelClass) {
                CreateRoomViewModel::class.java -> {
                    return CreateRoomViewModel(roomRepository) as T
                }
                else -> {
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

    fun onEvent(createRoomEvent: CreateRoomEvent) {
        when (createRoomEvent) {
            is CreateRoomEvent.TitleRoomChanged -> {
                _creatingRoomState.value = _creatingRoomState.value.copy(
                    title = createRoomEvent.title
                )
            }
            is CreateRoomEvent.CreateRoomSubmit -> {
                createRoomSubmit()
            }
        }
    }

    private fun createRoomSubmit() {
        viewModelScope.launch {
            val id = roomRepository.insertRoom(
                InsertedRoomDto(
                    title = _creatingRoomState.value.title.toString()
                )
            )

            _validationEventChannel.send(
                if (id != -1) {
                    _creatingRoomState.value.id = id
                    ValidationEvent.Success
                } else {
                    ValidationEvent.Failure
                }
            )

            Log.i("id", id.toString())
        }
    }
}