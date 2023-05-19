package com.example.quick_cheque.di

import com.example.quick_cheque.MainActivity
import com.example.quick_cheque.di.module.AppModule
import com.example.quick_cheque.di.module.DomainModule
import com.example.quick_cheque.di.module.RemoteModule
import com.example.quick_cheque.di.module.RoomModule
import com.example.quick_cheque.presentation.screen.auth_pages_fragment.LoginFragment
import com.example.quick_cheque.presentation.screen.auth_pages_fragment.RegisterFragment
import com.example.quick_cheque.presentation.screen.main_pages_fragments.CreateScreenFragment
import com.example.quick_cheque.presentation.screen.room_cheque_fragments.ChoiceChequeFragment
import com.example.quick_cheque.presentation.screen.room_cheque_fragments.ChoiceProductFragment
import com.example.quick_cheque.presentation.screen.room_cheque_fragments.ChoiceRoomFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, DomainModule::class, RoomModule::class, RemoteModule::class])
@Singleton
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
    fun injectRegisterFragment(registerFragment: RegisterFragment)
    fun injectLoginFragment(loginFragment: LoginFragment)
    fun injectCreateScreenFragment(createScreenFragment: CreateScreenFragment)

    fun injectChoiceRoomFragment(choiceRoomFragment: ChoiceRoomFragment)
    fun injectChoiceChequeFragment(choiceChequeFragment: ChoiceChequeFragment)
    fun injectChoiceProductFragment(choiceProductFragment: ChoiceProductFragment)
}