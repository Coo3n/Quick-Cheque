package com.example.quick_cheque.di.modules

import com.example.quick_cheque.domain.use_case.ValidateLoginUseCase
import com.example.quick_cheque.domain.use_case.ValidatePasswordUseCase
import com.example.quick_cheque.domain.use_case.ValidateRepeatedPasswordUseCase
import com.example.quick_cheque.presentation.screen.auth_pages_fragment.RegisterViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideValidateLoginUseCase(): ValidateLoginUseCase {
        return ValidateLoginUseCase()
    }

    @Provides
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    fun provideValidateRepeatedPasswordUseCase(): ValidateRepeatedPasswordUseCase {
        return ValidateRepeatedPasswordUseCase()
    }

    @Provides
    fun provideRegisterViewModelFactory(
        validateEmail: ValidateLoginUseCase,
        validatePassword: ValidatePasswordUseCase,
        validateRepeatedPassword: ValidateRepeatedPasswordUseCase
    ): RegisterViewModelFactory {
        return RegisterViewModelFactory(
            validateEmail,
            validatePassword,
            validateRepeatedPassword
        )
    }
}