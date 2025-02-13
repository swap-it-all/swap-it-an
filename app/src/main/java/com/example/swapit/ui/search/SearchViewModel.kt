package com.example.swapit.ui.search

import androidx.lifecycle.ViewModel
import com.example.swapit.data.datasource.local.model.search.PopularSearchTerm
import com.example.swapit.data.datasource.local.model.search.RecentSearchTerm

class SearchViewModel : ViewModel() {
    val allRecentSearchTerm: List<RecentSearchTerm> by lazy { RecentSearchTerm.values().toList() }
    val allPopularSearchTerm: List<PopularSearchTerm> by lazy { PopularSearchTerm.values().toList() }
}
