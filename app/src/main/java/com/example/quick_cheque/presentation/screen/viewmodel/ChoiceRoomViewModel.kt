package com.example.quick_cheque.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.domain.model.Room
import com.example.quick_cheque.presentation.events.ChoiceItemEvent
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChoiceRoomViewModel(
    private val roomRepository: RoomRepositoryImpl
) : ViewModel() {
    class ChoiceItemViewModelFactory(
        private val roomRepository: RoomRepositoryImpl
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when (modelClass) {
                ChoiceRoomViewModel::class.java -> {
                    ChoiceRoomViewModel(roomRepository) as T
                }
                else -> {
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

    data class ChoiceItemState(
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false
    )

    private var _roomListItems = MutableStateFlow<MutableList<Room>>(mutableListOf())
    val roomListItems = _roomListItems.asStateFlow()

    private var _filteredRoomListItems =
        MutableStateFlow<MutableList<Room>>(mutableListOf())
    val filteredRoomListItems = _filteredRoomListItems.asStateFlow()

    private val _roomItemState = MutableStateFlow(ChoiceItemState())
    val roomItemState = _roomItemState.asStateFlow()

    init {
        getRoomItem()
    }

    fun onEvent(choiceItemEvent: ChoiceItemEvent) {
        when (choiceItemEvent) {
            is ChoiceItemEvent.Refresh -> {
                getRoomItem()
            }
        }
    }

    private fun getRoomItem() {
        viewModelScope.launch {
            roomRepository.getChoiceItems(true).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _roomItemState.value = _roomItemState.value.copy(
                            isLoading = result.isLoading
                        )
                    }
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        val resultListItems = result.data?.toMutableList() ?: mutableListOf()
                        setFilteredListItems(resultListItems as MutableList<Room>)
                        setListItems(resultListItems as MutableList<Room>)
                    }
                }
            }
        }
    }


    private fun setListItems(listItems: MutableList<Room>) {
        _roomListItems.value = listItems
    }

    fun setFilteredListItems(filteredListItems: MutableList<Room>) {
        _filteredRoomListItems.value = filteredListItems
    }

    fun getListRoomItem() = _roomListItems.value
    fun getFilteredListRoomItem() = _filteredRoomListItems.value
}