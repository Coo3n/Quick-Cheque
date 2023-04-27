package com.example.quick_cheque.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.quick_cheque.R
import com.example.quick_cheque.page_elements.SwitchMenu
import kotlinx.coroutines.delay

class RegisterPage {
    private val loginField: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.input_login))
    private val passwordField: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
    private val repeatPasswordField: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.repeated_input_password))
    private val submitButton: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.register_btn))
    private val emailErrorText: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.email_error))
    private val passwordErrorText: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.password_error))
    private val repeatedPasswordErrorText: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.repeated_password_error))

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

    fun inputPassword(password: String): Boolean {
        passwordField.perform(
            click(),
            typeText(password),
            closeSoftKeyboard()
        )
        return true
    }

    fun inputRepeatedPassword(repeatedPassword: String): Boolean {
        repeatPasswordField.perform(
            click(),
            typeText(repeatedPassword),
            closeSoftKeyboard()
        )
        return true
    }

    fun submitRegisterButton() {
        submitButton.perform(click())
    }

    fun hasEmailError(expectedText: String)  {
        emailErrorText.check(ViewAssertions.matches(ViewMatchers.withText(expectedText)))
    }

}