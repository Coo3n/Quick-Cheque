package com.example.quick_cheque.page_elements

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import com.example.quick_cheque.R


val SwitchMenu: List<ViewInteraction> = listOf(
    Espresso.onView(ViewMatchers.withId(R.id.switch_button_register)),
    Espresso.onView(ViewMatchers.withId(R.id.switch_button_login))
)
