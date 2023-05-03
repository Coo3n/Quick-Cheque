package com.example.quick_cheque.domain.use_case

class ValidatePasswordUseCase {
    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Пароль не может быть пустым!"
            )
        }

        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "Пароль должен быть больше 8 символов!"
            )
        }

        val hasLetterAndNumber = password.any { it.isDigit() } && password.any { it.isLetter() }

        if (!hasLetterAndNumber) {
            return ValidationResult(
                successful = false,
                errorMessage = "Пароль должен содержать буквы и цифры"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}