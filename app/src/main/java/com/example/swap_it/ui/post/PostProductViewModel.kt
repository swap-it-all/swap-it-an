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

    fun multipleImages(uris: List<Uri>) {
        _selectedImageUris.value = uris.take(10)
    }

    val allQualitys: List<QualityOption> by lazy {
        QualityOption.entries.toList()
    }

    val allCategories: List<CategoryOption> by lazy {
        CategoryOption.entries.toList()
    }
}
