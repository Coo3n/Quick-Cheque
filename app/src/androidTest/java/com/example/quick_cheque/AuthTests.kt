package com.example.quick_cheque


import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.example.quick_cheque.feature.LoadableState
import com.example.quick_cheque.pages.RegisterPage
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AuthTests : BaseTest() {
    private lateinit var registerPage: RegisterPage

    companion object {
        const val EMPTY_LOGIN = ""
        const val LOGIN = "ilya@mail.ru"
        const val PASSWORD = "bkmz123123"
        const val REPEATED_PASSWORD = "bkmz123123"
    }

    @Before
    fun initData() {
        registerPage = RegisterPage()
    }

    @Test
    fun unCorrectLoginAuthTest() {
        registerPage.checkoutLoginPage()
            .inputLogin(EMPTY_LOGIN)
            .inputPassword(PASSWORD)
            .inputRepeatedPassword(REPEATED_PASSWORD)
            .submitRegisterButton()

        registerPage.hasEmailError("Почта пустая!")
    }

    @Test
    fun correctAuthTest() {
        val createRoomFragment = registerPage.checkoutLoginPage()
            .inputLogin(LOGIN)
            .inputPassword(PASSWORD)
            .inputRepeatedPassword(REPEATED_PASSWORD)
            .submitRegisterButton()

        createRoomFragment.setLoadingState()
            .apply {
                assertThat(getCurrentState(), instanceOf(LoadableState.Loading::class.java))
            }
            .isVisibleCreateRoomButton()
            .setSuccessState()
            .apply {
                assertThat(getCurrentState(), instanceOf(LoadableState.Success::class.java))
            }
    }
}