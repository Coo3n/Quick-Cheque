package com.example.quick_cheque.presentation.screen.room_cheque_fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quick_cheque.domain.repository.ChoiceItemRepository

class ChoiceItemViewModelFactory(
    private val roomRepository: ChoiceItemRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when (modelClass) {
            ChoiceItemViewModel::class.java -> {
                return ChoiceItemViewModel(roomRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}