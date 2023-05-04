package com.example.quick_cheque.di.modules

import com.example.quick_cheque.data.remote.QuickChequeApi
import com.example.quick_cheque.data.repository.AuthenticationRepositoryImpl
import com.example.quick_cheque.domain.repository.AuthenticationRepository
import com.example.quick_cheque.domain.use_case.AuthUseCase
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
    fun provideAuthenticationRepository(quickChequeApi: QuickChequeApi): AuthenticationRepository {
        return AuthenticationRepositoryImpl(quickChequeApi)
    }

    @Provides
    fun provideAuthUseCase(authenticationRepository: AuthenticationRepository): AuthUseCase {
        return AuthUseCase(authenticationRepository)
    }

    @Provides
    fun provideRegisterViewModelFactory(
        validateEmail: ValidateLoginUseCase,
        validatePassword: ValidatePasswordUseCase,
        validateRepeatedPassword: ValidateRepeatedPasswordUseCase,
        authUseCase: AuthUseCase
    ): RegisterViewModelFactory {
        return RegisterViewModelFactory(
            validateEmail,
            validatePassword,
            validateRepeatedPassword,
            authUseCase
        )
    }
}