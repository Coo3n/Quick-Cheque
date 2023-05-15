package com.example.quick_cheque.feature

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

class LoadableComponent {
    private var currentState: LoadableState = LoadableState.Loading

    fun setLoadingState() {
        currentState = LoadableState.Loading
    }

    fun setSuccessState() {
        currentState = LoadableState.Success
    }

    fun getCurrentState(): LoadableState {
        return currentState
    }
}