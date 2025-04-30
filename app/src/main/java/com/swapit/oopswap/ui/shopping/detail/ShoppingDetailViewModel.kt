package com.swapit.oopswap.ui.shopping.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swapit.oopswap.data.mapper.toDomain
import com.swapit.oopswap.domain.model.product.detail.ProductDetail
import com.swapit.oopswap.domain.model.product.detail.ProductDetailUser
import com.swapit.oopswap.domain.repository.ProductRepository
import com.swapit.oopswap.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class ShoppingDetailViewModel(private val repository: ProductRepository, private val _goodsId: String) : ViewModel() {
    val goodsId: String get() = _goodsId
    private val shoppingDetailContents =
        mutableStateOf(
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
                trade = null,
                createdAt = "",
            ),
        )
    val detailContents: ProductDetail get() = shoppingDetailContents.value
    val dropMenuExpanded = mutableStateOf(false)
    val showDeleteDialog = mutableStateOf(false)

    fun deleteProduct() {
        viewModelScope.launch {
            repository.deleteProduct(goodsId.toLong())
        }
    }

    fun fetchProductDetail() {
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
