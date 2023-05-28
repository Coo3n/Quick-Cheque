package com.example.quick_cheque.denisstrizhkin.pages

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.quick_cheque.R
import com.example.quick_cheque.denisstrizhkin.util.EnterText

class JoinRoomPage {
    private val btnJoin = onView(withId(R.id.join_button))
    private val fieldRoomId = onView(withId(R.id.fragment_join_room_field_room_id))

    fun typeRoomId(id: Int) : JoinRoomPage {
        EnterText(fieldRoomId).enter(id.toString())
        return this
    }

    fun submitRoomData() : JoinRoomPage {
        btnJoin.perform(click())
        return this
    }

    fun checkPage() {
        btnJoin.check(matches(isDisplayed()))
    }
}