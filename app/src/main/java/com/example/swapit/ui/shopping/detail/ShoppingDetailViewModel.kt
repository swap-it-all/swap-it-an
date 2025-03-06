package com.example.swapit.ui.shopping.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.product.detail.ProductDetail
import com.example.swapit.domain.model.product.detail.ProductDetailUser
import com.example.swapit.domain.repository.ProductRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class ShoppingDetailViewModel(repository: ProductRepository, private val _goodsId: String) : ViewModel() {
    val goodsId: String get() = _goodsId
    private val shoppingDetailContents =
        mutableStateOf<ProductDetail>(
            ProductDetail(
                goodsId = 0,
                user = ProductDetailUser(userId = 1, nickname = "", profileImageUrl = "", userRating = 1.1),
                category = "",
                title = "",
                price = 0,
                quality = "",
                content = "",
                goodsTradeStatus = "",
                placeName = "",
                viewCount = 0,
                imageUri = emptyList(),
                createdAt = "",
            ),
        )

    val detailContents: ProductDetail get() = shoppingDetailContents.value

    init {
        viewModelScope.launch {
            shoppingDetailContents.value = repository.productDetailResults(_goodsId).toDomain()
        }
    }

    companion object {
        private const val TAG = "ShoppingDetailViewModel"

        fun factory(
            repository: ProductRepository,
            goodsId: String,
        ): ViewModelProvider.Factory =
            BaseViewModelFactory {
                ShoppingDetailViewModel(
                    repository = repository,
                    _goodsId = goodsId,
                )
            }
    }
}
