package com.example.quick_cheque.domain.use_case

class ValidateUsername {
    fun execute(username: String): ValidationResult {
        if (username.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Username не может быть пустым!"
            )
        }

        if (username.length < 5) {
            return ValidationResult(
                successful = false,
                errorMessage = "Пароль не совпадает с исходным!"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}