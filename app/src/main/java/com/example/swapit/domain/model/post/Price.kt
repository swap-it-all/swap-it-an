package com.example.swapit.domain.model.post

data class Price(private val value: String = DEFAULT_VALUE) {
    fun validationResult(): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.EMPTY
            !isValidFormat() -> ValidationResult.INVALID_FORMAT
            !isValidRange() -> ValidationResult.INVALID_RANGE
            else -> ValidationResult.SUCCESS
        }
    }

    private fun isValidFormat(): Boolean = value.toIntOrNull() != null

    private fun isValidRange(): Boolean {
        val numericValue = value.toIntOrNull() ?: return false
        return numericValue in PRODUCT_RANGE
    }

    companion object {
        const val DEFAULT_VALUE = "0"
        val PRODUCT_RANGE = 0..100_000_000
    }
}
