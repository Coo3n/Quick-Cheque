package com.example.quick_cheque.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.example.quick_cheque.R
import com.example.quick_cheque.feature.LoadableComponent
import com.example.quick_cheque.feature.LoadableState

class CreateRoomPage {
    private val createRoomButton: ViewInteraction by lazy { Espresso.onView(ViewMatchers.withId(R.id.rectangle1)) }
    private val loadableComponent = LoadableComponent()

    fun setLoadingState(): CreateRoomPage {
        loadableComponent.setLoadingState()
        return this
    }

    fun setSuccessState(): CreateRoomPage {
        loadableComponent.setSuccessState()
        return this
    }

    fun getCurrentState(): LoadableState {
        return loadableComponent.getCurrentState()
    }

    fun isVisibleCreateRoomButton(): CreateRoomPage {
        createRoomButton.check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
        return this
    }
}