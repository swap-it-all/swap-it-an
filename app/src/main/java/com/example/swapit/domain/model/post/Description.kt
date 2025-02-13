package com.example.swapit.domain.model.post

data class Description(val value: String = DEFAULT_VALUE) {
    fun validationResult(): ValidationResult {
        return when {
            value.isEmpty() -> ValidationResult.EMPTY
            !isValidRange() -> ValidationResult.INVALID_RANGE
            else -> ValidationResult.SUCCESS
        }
    }

    private fun isValidRange(): Boolean = value.length in DESCRIPTION_RANGE

    companion object {
        const val DEFAULT_VALUE = "0"
        val DESCRIPTION_RANGE = 0..300
    }
}
