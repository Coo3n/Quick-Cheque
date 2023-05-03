package com.example.quick_cheque.domain.use_case

import android.util.Patterns

class ValidateLoginUseCase {
    fun execute(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Почта пустая!"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Это неверный формат почты!"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}