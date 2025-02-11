package com.example.swap_it.domain.model.post

data class Name(private val value: String = DEFAULT_VALUE) {
    fun validationResult(): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.EMPTY
            !isValidLength() -> ValidationResult.INVALID_LENGTH
            else -> ValidationResult.SUCCESS
        }
    }

    private fun isValidLength(): Boolean = value.length in PRODUCT_NAME_RANGE

    companion object {
        const val DEFAULT_VALUE = ""
        val PRODUCT_NAME_RANGE = 2..20
    }
}
