package com.example.quick_cheque.screens.viewmodels

import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModel
import com.example.quick_cheque.model.Cheque
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChoiceChequeViewModel : ViewModel() {
    private var _actionBar = MutableStateFlow<ActionBar?>(null)
    val actionBar = _actionBar.asStateFlow()

    private var _listItems = MutableStateFlow<MutableList<Cheque>>(mutableListOf())
    val listItems = _listItems.asStateFlow()

    private var _filteredListItems = MutableStateFlow<MutableList<Cheque>>(mutableListOf())
    val filteredListItems = _filteredListItems.asStateFlow()

    private var _choiceCurrentPosition = MutableStateFlow(0)
    val choiceCurrentPosition = _choiceCurrentPosition.asStateFlow()

    private var _lastQuerySearch = MutableStateFlow("")
    val lastQuerySearch = _lastQuerySearch.asStateFlow()

    fun setChoiceCurrentPosition(choiceCurrentPosition: Int) {
        _choiceCurrentPosition.value = choiceCurrentPosition
    }

    fun setActionBar(actionBar: ActionBar) {
        _actionBar.value = actionBar
    }

    fun setListItems(listItems: MutableList<Cheque>) {
        _listItems.value = listItems
    }

    fun setFilteredListItems(filteredListItems: MutableList<Cheque>) {
        _filteredListItems.value = filteredListItems
    }

    fun setLastQuerySearch(query: String) {
        _lastQuerySearch.value = query
    }
}
