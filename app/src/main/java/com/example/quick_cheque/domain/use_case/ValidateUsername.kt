package com.example.quick_cheque.domain.use_case

class ValidateUsername {
    fun execute(username: String): ValidationResult {
        if (username.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Username не может быть пустым!"
            )
        }

        if (username.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = "Username должен быть побольше!"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}