package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import androidx.lifecycle.ViewModel
import com.example.quick_cheque.domain.model.ChoiceItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChoiceItemViewModel : ViewModel() {
    private var _choiceCurrentPosition = MutableStateFlow(0)
    val choiceCurrentPosition = _choiceCurrentPosition.asStateFlow()

    private var _listItems = MutableStateFlow<MutableList<ChoiceItem>>(mutableListOf())
    val listItems = _listItems.asStateFlow()

    private var _filteredListItems = MutableStateFlow<MutableList<ChoiceItem>>(mutableListOf())
    val filteredListItems = _filteredListItems.asStateFlow()

    fun setChoiceCurrentPosition(choiceCurrentPosition: Int) {
        _choiceCurrentPosition.value = choiceCurrentPosition
    }

    fun setListItems(listItems: MutableList<ChoiceItem>) {
        _listItems.value = listItems
    }

    fun setFilteredListItems(filteredListItems: MutableList<ChoiceItem>) {
        _filteredListItems.value = filteredListItems
    }
}