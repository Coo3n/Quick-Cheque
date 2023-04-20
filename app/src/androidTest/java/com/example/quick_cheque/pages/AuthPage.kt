package com.example.quick_cheque.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.quick_cheque.R
import com.example.quick_cheque.page_elements.SwitchMenu

class AuthPage {
    private val loginField: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.input_login))
    private val passwordField: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
    private val repeatPasswordField: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.repeated_input_password))

    fun checkoutLoginPage() {
        SwitchMenu[0].check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        SwitchMenu[0].perform(click())
    }

    fun inputLogin(login: String): Boolean {
        loginField.perform(
            click(),
            typeText(login),
            closeSoftKeyboard()
        )
        return true
    }
}