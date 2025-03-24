package com.swapit.company.data.datasource.local.model.bottomsheet

enum class SortOption(val option: String, val key: String) {
    POPULAR("인기순", "popular"),
    RECENT("최신순", "recent"),
    PRICE_HIGH("가격 높은 순", "priceHigh"),
    PRICE_ROW("가격 낮은 순", "priceLow"),
}
