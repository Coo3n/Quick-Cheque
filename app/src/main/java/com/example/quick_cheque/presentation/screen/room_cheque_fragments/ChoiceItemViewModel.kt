package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quick_cheque.domain.model.ChoiceItem
import com.example.quick_cheque.domain.repository.ChoiceItemRepository
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChoiceItemViewModel(
    private val choiceItemRepository: ChoiceItemRepository
) : ViewModel() {
    private var _choiceLastPosition = MutableStateFlow(0)
    val choiceLastPosition = _choiceLastPosition.asStateFlow()

    private var _listItems = MutableStateFlow<MutableList<ChoiceItem>>(mutableListOf())
    val listItems = _listItems.asStateFlow()

    private var _filteredListItems = MutableStateFlow<MutableList<ChoiceItem>>(mutableListOf())
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
            choiceItemRepository.getChoiceItems(true).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _choiceItemState.value = _choiceItemState.value.copy(
                            isLoading = result.isLoading
                        )
                    }
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        val resultListItems = result.data?.toMutableList() ?: mutableListOf()
                        setFilteredListItems(resultListItems)
                        setListItems(resultListItems)
                    }
                }
            }
        }
    }

    fun setChoiceCurrentPosition(choiceCurrentPosition: Int) {
        _choiceLastPosition.value = choiceCurrentPosition
    }

    fun setListItems(listItems: MutableList<ChoiceItem>) {
        _listItems.value = listItems
    }

    fun setFilteredListItems(filteredListItems: MutableList<ChoiceItem>) {
        _filteredListItems.value = filteredListItems
    }

    fun setChoiceLastPosition(position: Int) {
        _choiceLastPosition.value = position
    }

    fun getChoiceItemOnPosition(position: Int): ChoiceItem = _filteredListItems.value[position]


    fun changeChoiceItemState(
        currentClickedChoiceItem: Int,
        prevChoiceItem: ChoiceItem,
        currentChoiceItem: ChoiceItem
    ) {
        val items = _filteredListItems.value.toMutableList()
        items[_choiceLastPosition.value] = prevChoiceItem
        items[currentClickedChoiceItem] = currentChoiceItem
        setFilteredListItems(items)
    }
}