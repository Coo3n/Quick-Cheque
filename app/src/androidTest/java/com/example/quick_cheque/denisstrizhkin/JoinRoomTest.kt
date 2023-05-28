package com.example.quick_cheque.denisstrizhkin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.quick_cheque.R
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test

class JoinRoomTest : Base() {
    @Test
    fun switchToJoinRoomPage() {
        val roomsPage = getRoomsPage()
        val joinRoomPage = roomsPage.switchToJoinRoom()
        joinRoomPage.checkPage()
    }

    @Test
    fun joinRoom() {
        val joinRoomPage = getRoomsPage().switchToJoinRoom()
        joinRoomPage
            .typeRoomId(123)
            .submitRoomData()
        onView(withId(R.id.stagesMenu)).check(matches(isDisplayed()))
    }
}