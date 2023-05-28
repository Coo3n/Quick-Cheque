package com.example.quick_cheque.denisstrizhkin.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.quick_cheque.R

class RoomsPage {
    private val navToJoinRoom = onView(withId(R.id.joinScreenFragment))
    private val textNoRoomsYet = onView(withId(R.id.no_rooms_yet_text))

    fun switchToJoinRoom() : JoinRoomPage {
        navToJoinRoom.perform(click())
        return JoinRoomPage()
    }

    fun checkPage() {
        textNoRoomsYet.check(matches(isDisplayed()))
    }
}