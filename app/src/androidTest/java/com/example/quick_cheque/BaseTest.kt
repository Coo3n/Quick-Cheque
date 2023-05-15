package com.example.quick_cheque

import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule

open class BaseTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)
}