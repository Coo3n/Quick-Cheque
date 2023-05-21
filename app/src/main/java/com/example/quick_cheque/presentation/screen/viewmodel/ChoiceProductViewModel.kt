package com.example.quick_cheque.presentation.screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.quick_cheque.data.repository.ProductRepositoryImpl
import com.example.quick_cheque.data.repository.RoomRepositoryImpl
import com.example.quick_cheque.domain.model.Product
import com.example.quick_cheque.presentation.events.ChoiceItemEvent
import com.example.quick_cheque.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChoiceProductViewModel(
    private val productRepository: ProductRepositoryImpl
) : ViewModel() {
    class ChoiceItemViewModelFactory(
        private val productRepository: ProductRepositoryImpl
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when (modelClass) {
                ChoiceProductViewModel::class.java -> {
                    ChoiceProductViewModel(productRepository) as T
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

    private var _listItems = MutableStateFlow<MutableList<Product>>(mutableListOf())
    val listItems = _listItems.asStateFlow()

    private var _filteredListItems = MutableStateFlow<MutableList<Product>>(mutableListOf())
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
            productRepository.getProducts(true).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _choiceItemState.value = _choiceItemState.value.copy(
                            isLoading = result.isLoading
                        )
                    }
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        val resultListItems = result.data?.toMutableList() ?: mutableListOf()
                        setFilteredListItems(resultListItems as MutableList<Product>)
                        setListItems(resultListItems as MutableList<Product>)
                    }
                }
            }
        }
    }


    fun setListItems(listItems: MutableList<Product>) {
        _listItems.value = listItems
    }

    fun setFilteredListItems(filteredListItems: MutableList<Product>) {
        _filteredListItems.value = filteredListItems
    }
}