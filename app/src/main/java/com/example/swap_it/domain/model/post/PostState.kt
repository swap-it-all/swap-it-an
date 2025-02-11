package com.example.swap_it.domain.model.post

import coil3.Uri
import com.example.swap_it.data.datasource.local.model.post.CategoryOption
import com.example.swap_it.data.datasource.local.model.post.QualityOption

data class PostState(
    val image: List<Uri>,
    val name: Name,
    val price: Price,
    val location: Location,
    val description: Description,
    val quality: QualityOption? = null,
    val category: CategoryOption? = null,
) {
    fun isValid(): Boolean {
        return name.validationResult() == ValidationResult.SUCCESS &&
                price.validationResult() == ValidationResult.SUCCESS &&
                location.validationResult() == ValidationResult.SUCCESS &&
                description.validationResult() == ValidationResult.SUCCESS &&
                quality != null &&
                category != null &&
                image.isNotEmpty()
    }
}
