package com.example.swap_it.ui.post

import androidx.lifecycle.ViewModel
import com.example.swap_it.data.datasource.local.model.post.CategoryOption
import com.example.swap_it.data.datasource.local.model.post.QualityOption

class PostProductViewModel : ViewModel() {
    val allQualitys: List<QualityOption> by lazy {
        QualityOption.entries.toList()
    }

    val allCategories: List<CategoryOption> by lazy {
        CategoryOption.entries.toList()
    }
}
