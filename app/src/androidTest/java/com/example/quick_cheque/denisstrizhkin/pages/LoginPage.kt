package com.example.quick_cheque.denisstrizhkin.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.quick_cheque.R
import com.example.quick_cheque.denisstrizhkin.util.EnterText

class LoginPage {
    private val fieldEmail = onView(withId(R.id.fragment_login_email_field_input))
    private val fieldPassword = onView(withId(R.id.fragment_login_password_1_field_input))
    private val btnSubmit = onView(withId(R.id.login_btn))

    fun typeEmail(email: String): LoginPage {
        EnterText(fieldEmail).enter(email)
        return this
    }

    fun typePassword(password: String): LoginPage {
        EnterText(fieldPassword).enter(password)
        Thread.sleep(500)
        return this
    }

    fun submitLoginData(): RoomsPage {
        btnSubmit.perform(click())
        return RoomsPage()
    }
}
