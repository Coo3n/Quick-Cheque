package com.example.quick_cheque


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.example.quick_cheque.pages.RegisterPage
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AuthTests {
    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var registerPage: RegisterPage

    @Before
    fun initData() {
        registerPage = RegisterPage()
    }

    @Test
    fun unCorrectLoginAuthTest() {
        registerPage.checkoutLoginPage()
        registerPage.inputPassword("bkmz")
        registerPage.inputRepeatedPassword("bkmz")
        registerPage.submitRegisterButton()
        registerPage.hasEmailError("Почта пустая!")
    }

    @Test
    fun correctAuthTest() {
        registerPage.checkoutLoginPage()
        registerPage.inputLogin("ilya@mail.ru")
        registerPage.inputPassword("bkmz123123")
        registerPage.inputRepeatedPassword("bkmz123123")
        registerPage.submitRegisterButton()
    }
}