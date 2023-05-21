package com.example.quick_cheque.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quick_cheque.data.remote.dto.ChoiceItemResponseDto
import com.example.quick_cheque.data.repository.ChequeRepositoryImpl
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.domain.model.ChequeListItem
import com.example.quick_cheque.domain.model.ChoiceItem
import com.example.quick_cheque.presentation.events.ChoiceItemEvent
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChoiceChequeViewModel(
    private val chequeRepository: ChequeRepositoryImpl
) : ViewModel() {
    class ChoiceChequeViewModelFactory(
        private val chequeRepository: ChequeRepositoryImpl
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when (modelClass) {
                ChoiceChequeViewModel::class.java -> {
                    ChoiceChequeViewModel(chequeRepository) as T
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

    private var _choiceLastPosition = MutableStateFlow(0)
    val choiceLastPosition = _choiceLastPosition.asStateFlow()

    private var _listItems = MutableStateFlow<MutableList<ChequeListItem>>(mutableListOf())
    val listItems = _listItems.asStateFlow()

    private var _filteredListItems = MutableStateFlow<MutableList<ChequeListItem>>(mutableListOf())
    val filteredListItems = _filteredListItems.asStateFlow()

    private val _choiceItemState = MutableStateFlow(ChoiceItemState())
    val choiceItemState = _choiceItemState.asStateFlow()

    fun initChoiceItems() {
        getChoiceItem()
    }

    fun onEvent(choiceItemEvent: ChoiceItemEvent) {
        when (choiceItemEvent) {
            is ChoiceItemEvent.Refresh -> {
                getChoiceItem()
            }
        }
    }

    private fun getChoiceItem() {
        viewModelScope.launch {
            chequeRepository.getCheques(
                choiceItemResponseDto = ChoiceItemResponseDto(id = 1),
                fetchFromRemote = true
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _choiceItemState.value = _choiceItemState.value.copy(
                            isLoading = result.isLoading
                        )
                    }
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        val resultListItems = result.data?.toMutableList() ?: mutableListOf()
                        setFilteredListItems(resultListItems as MutableList<ChequeListItem>)
                        setListItems(resultListItems as MutableList<ChequeListItem>)
                    }
                }
            }
        }
    }

    fun setChoiceCurrentPosition(choiceCurrentPosition: Int) {
        _choiceLastPosition.value = choiceCurrentPosition
    }

    fun setListItems(listItems: MutableList<ChequeListItem>) {
        _listItems.value = listItems
    }

    fun setFilteredListItems(filteredListItems: MutableList<ChequeListItem>) {
        _filteredListItems.value = filteredListItems
    }

    fun getChoiceItemOnPosition(position: Int): ChoiceItem = _filteredListItems.value[position]

    fun changeChoiceItemState(
        currentClickedChoiceItem: Int,
        prevChoiceItem: ChequeListItem,
        currentChoiceItem: ChequeListItem
    ) {
        val items = _filteredListItems.value.toMutableList()
        items[_choiceLastPosition.value] = prevChoiceItem
        items[currentClickedChoiceItem] = currentChoiceItem
        setFilteredListItems(items)
    }
}