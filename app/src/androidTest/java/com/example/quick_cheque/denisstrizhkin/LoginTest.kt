package com.example.quick_cheque.denisstrizhkin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.quick_cheque.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class LoginTest : Base() {
    @Test
    fun successfulLogin() {
        val roomsPage = getRoomsPage()
        roomsPage.checkPage()
        onView(withId(R.id.main_bottom_nav)).check(matches(isDisplayed()))
    }
}
