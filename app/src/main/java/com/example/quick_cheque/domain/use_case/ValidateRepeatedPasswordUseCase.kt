package com.example.quick_cheque.domain.use_case

class ValidateRepeatedPasswordUseCase {
    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "Пароль не совпадает с исходным!"
            )
        }

        if (repeatedPassword.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage  = "Пароль не может быть пустым!"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}