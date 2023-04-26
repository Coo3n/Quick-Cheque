package com.example.quick_cheque.domain.use_case

class ValidateRepeatedPassword {
    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if (password != repeatedPassword) {
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