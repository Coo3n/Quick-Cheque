package com.example.quick_cheque.screens.viewmodels

import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ToolBarViewModel : ViewModel() {
    private var _actionBar = MutableStateFlow<ActionBar?>(null)
    val actionBar = _actionBar.asStateFlow()

    private var _lastQuerySearch = MutableStateFlow("")
    val lastQuerySearch = _lastQuerySearch.asStateFlow()

    private val _isExpandedSearchItem = MutableStateFlow(false)
    val isExpandedSearchItem = _isExpandedSearchItem.asStateFlow()

    fun setLastQuerySearch(query: String) {
        _lastQuerySearch.value = query
    }

    fun isLastQuerySearchEmpty(): Boolean = _lastQuerySearch.value.isEmpty()

    fun setExpandedSearchItem(expandedState: Boolean) {
        _isExpandedSearchItem.value = expandedState
    }

    fun setActionBar(actionBar: ActionBar) {
        _actionBar.value = actionBar
    }


    fun setVisibleHomeButton(isVisible: Boolean) {
        actionBar.value?.setDisplayHomeAsUpEnabled(isVisible)
    }

    fun setVisibleToolbar(isVisible: Boolean) {
        if (isVisible) {
            actionBar.value?.show()
        } else {
            actionBar.value?.hide()
        }
    }
}