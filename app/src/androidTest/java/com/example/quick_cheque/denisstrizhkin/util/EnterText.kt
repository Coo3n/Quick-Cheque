package com.example.quick_cheque.denisstrizhkin.util

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText

class EnterText (private val vi: ViewInteraction) {
    fun enter(text: String) {
        vi.perform(
            click(),
            typeText(text),
            closeSoftKeyboard()
        )
    }
}