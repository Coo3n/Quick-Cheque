package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import androidx.lifecycle.ViewModel
import com.example.quick_cheque.domain.model.ChoiceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChoiceItemViewModel : ViewModel() {
    private var _choiceLastPosition = MutableStateFlow(0)
    val choiceLastPosition = _choiceLastPosition.asStateFlow()

    private var _listItems = MutableStateFlow<MutableList<ChoiceItem>>(mutableListOf())
    val listItems = _listItems.asStateFlow()

    private var _filteredListItems = MutableStateFlow<MutableList<ChoiceItem>>(mutableListOf())
    val filteredListItems = _filteredListItems.asStateFlow()

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

    fun getFilteredListItems() = _filteredListItems.value

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