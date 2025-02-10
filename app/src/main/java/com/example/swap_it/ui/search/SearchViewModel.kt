package com.example.swap_it.ui.search

import androidx.lifecycle.ViewModel
import com.example.swap_it.data.datasource.local.model.search.CurrentSearch
import com.example.swap_it.data.datasource.local.model.search.PopularSearch

class SearchViewModel : ViewModel() {
    val allCurrentSearch: List<CurrentSearch> by lazy { CurrentSearch.values().toList() }
    val allPopularSearch: List<PopularSearch> by lazy { PopularSearch.values().toList() }
}