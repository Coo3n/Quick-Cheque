package com.example.quick_cheque.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.quick_cheque.R
import com.example.quick_cheque.feature.LoadableComponent
import com.example.quick_cheque.feature.LoadableState
import com.example.quick_cheque.page_elements.SwitchMenu

class RegisterPage {
    private val loginField: ViewInteraction by lazy { onView(withId(R.id.input_login)) }
    private val passwordField: ViewInteraction by lazy { onView(withId(R.id.input_password)) }
    private val repeatPasswordField: ViewInteraction by lazy { onView(withId(R.id.repeated_input_password)) }
    private val submitButton: ViewInteraction by lazy { onView(withId(R.id.register_btn)) }
    private val emailErrorText: ViewInteraction by lazy { onView(withId(R.id.email_error)) }

    fun checkoutLoginPage(): RegisterPage {
        SwitchMenu[0].check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )

        SwitchMenu[0].perform(click())
        return this
    }

    fun inputLogin(login: String): RegisterPage {
        loginField.performActions(login)
        return this
    }

    fun inputPassword(password: String): RegisterPage {
        passwordField.performActions(password)
        return this
    }

    fun inputRepeatedPassword(repeatedPassword: String): RegisterPage {
        repeatPasswordField.performActions(repeatedPassword)
        return this
    }

    fun submitRegisterButton(): CreateRoomPage {
        submitButton.perform(click())
        return CreateRoomPage()
    }

    fun hasEmailError(expectedText: String) {
        emailErrorText.check(
            ViewAssertions.matches(
                ViewMatchers.withText(expectedText)
            )
        )
    }

    private fun ViewInteraction.performActions(data: String) {
        this.perform(
            click(),
            typeText(data),
            closeSoftKeyboard()
        )
    }
}