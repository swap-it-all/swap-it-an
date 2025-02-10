package com.example.swap_it.ui.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import coil3.Uri
import com.example.swap_it.data.datasource.local.model.post.CategoryOption
import com.example.swap_it.data.datasource.local.model.post.QualityOption

class PostProductViewModel : ViewModel() {

    private val _selectedImageUris = mutableStateOf<List<Uri>>(emptyList())
    val selectedImageUris: State<List<Uri>> = _selectedImageUris

    val productName = mutableStateOf("")
    val productPrice = mutableStateOf("")
    val productLocation = mutableStateOf("")
    val productDescription = mutableStateOf("")

    val allQualitys: List<QualityOption> by lazy {
        QualityOption.entries.toList()
    }
    private val _selectedQuality = mutableStateOf<QualityOption?>(null)
    val selectedQuality: State<QualityOption?> = _selectedQuality

    val allCategories: List<CategoryOption> by lazy {
        CategoryOption.entries.toList()
    }
    private val _selectedCategory = mutableStateOf<CategoryOption?>(null)
    val selectedCategory: State<CategoryOption?> = _selectedCategory

    fun multipleImages(uris: List<Uri>) {
        _selectedImageUris.value = uris.take(10)
    }

    fun updateName(name: String) {
        productName.value = name
    }

    fun updatePrice(price: String) {
        productPrice.value = price
    }

    fun updateLocation(location: String) {
        productLocation.value = location
    }

    fun updateDescription(description: String) {
        productDescription.value = description
    }

    fun updateQuality(quality: QualityOption) {
        _selectedQuality.value = quality
    }

    fun updateCategory(category: CategoryOption) {
        _selectedCategory.value = category
    }
}
