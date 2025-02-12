package com.example.swap_it.ui.search

import androidx.lifecycle.ViewModel
import com.example.swap_it.data.datasource.local.model.search.RecentSearchTerm
import com.example.swap_it.data.datasource.local.model.search.PopularSearchTerm

class SearchViewModel : ViewModel() {
    val allRecentSearchTerm: List<RecentSearchTerm> by lazy { RecentSearchTerm.values().toList() }
    val allPopularSearchTerm: List<PopularSearchTerm> by lazy { PopularSearchTerm.values().toList() }
}