package com.example.quick_cheque.feature

sealed class LoadableState {
    object Loading : LoadableState()
    object Success : LoadableState()
    data class Error(val errorMessage: String) : LoadableState()
}