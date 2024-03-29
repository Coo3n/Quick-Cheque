package com.example.quick_cheque

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.quick_cheque.pages.AuthPage
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AuthTests {
    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun correctAuthTest() {
        val authPage = AuthPage()
        authPage.checkoutLoginPage()
        authPage.inputLogin("sdsd")
    }
}