package com.example.quick_cheque.domain.use_case

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
