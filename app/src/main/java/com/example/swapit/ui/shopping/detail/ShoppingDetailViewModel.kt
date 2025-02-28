package com.example.swapit.ui.shopping.detail

import ShoppingViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailData
import com.example.swapit.domain.model.shopping.detail.ShoppingDetailUser
import com.example.swapit.domain.repository.ShoppingDetailRepository
import com.example.swapit.domain.repository.ShoppingRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class ShoppingDetailViewModel(repository: ShoppingDetailRepository,private val goodsId: String) : ViewModel() {
    private val shoppingDetailContents = mutableStateOf<ShoppingDetailData>(
        ShoppingDetailData(
            goodsId = 0,
            user = ShoppingDetailUser(userId = 1, nickname = "", profileImageUrl = "",userRating = 1),
            category = "",
            title = "",
            price = 0,
            quality = "",
            content = "",
            goodsTradeStatus = "",
            placeName = "",
            viewCount = 0,
            imageUri = emptyList(),
            createdAt = ""
        )
    )

    val detailContents: ShoppingDetailData get() = shoppingDetailContents.value

    init {
        viewModelScope.launch {
            shoppingDetailContents.value = repository.shoppingDetailResults(goodsId).toDomain()
        }
    }

    companion object {
        private const val TAG = "ShoppingDetailViewModel"

        fun factory(repository: ShoppingDetailRepository,goodsId: String): ViewModelProvider.Factory =
            BaseViewModelFactory {
                ShoppingDetailViewModel(
                    repository = repository,
                    goodsId = goodsId
                )
            }
    }

}