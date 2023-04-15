package com.example.quick_cheque.screens.room_cheque_fragments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChoiceChequeViewModel : ViewModel() {
    //private var _listItems: MutableList<ChequeListItem>

    private var _choiceCurrentPosition = MutableStateFlow(0)
    val choiceCurrentPosition: StateFlow<Int> = _choiceCurrentPosition.asStateFlow()
}