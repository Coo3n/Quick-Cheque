package com.example.quick_cheque.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import com.example.quick_cheque.R

class AuthPage {
    val loginField: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.input_login))
    val passwordField: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.input_password))
    val repeatPasswordField: ViewInteraction =
        Espresso.onView(ViewMatchers.withId(R.id.repeated_input_password))
}