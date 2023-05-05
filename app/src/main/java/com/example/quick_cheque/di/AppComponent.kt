package com.example.quick_cheque.di

import com.example.quick_cheque.MainActivity
import com.example.quick_cheque.MyApp
import com.example.quick_cheque.di.modules.AppModule
import com.example.quick_cheque.di.modules.DomainModule
import com.example.quick_cheque.di.modules.RemoteModule
import com.example.quick_cheque.di.modules.RoomModule
import com.example.quick_cheque.presentation.screen.auth_pages_fragment.LoginFragment
import com.example.quick_cheque.presentation.screen.auth_pages_fragment.RegisterFragment
import com.example.quick_cheque.presentation.screen.room_cheque_fragments.ChoiceRoomFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, RoomModule::class, RemoteModule::class])
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
    fun injectRegisterFragment(registerFragment: RegisterFragment)
    fun injectChoiceRoomFragment(choiceRoomFragment: ChoiceRoomFragment)
    fun injectLoginFragment(loginFragment: LoginFragment)
}