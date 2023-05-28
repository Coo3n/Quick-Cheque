package com.example.quick_cheque.denisstrizhkin

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.quick_cheque.MainActivity
import com.example.quick_cheque.denisstrizhkin.pages.LoginPage
import com.example.quick_cheque.denisstrizhkin.pages.RoomsPage
import com.example.quick_cheque.denisstrizhkin.util.AppData
import org.junit.Rule

open class Base {
    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    private val loginPage = LoginPage()

    protected fun getRoomsPage() : RoomsPage {
        return loginPage
            .typeEmail(AppData.EMAIL)
            .typePassword(AppData.PASSWORD)
            .submitLoginData()
    }
}