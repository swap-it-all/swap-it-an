package com.swapit.oopswap.ui.shopping.detail.select

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModelProvider
import com.swapit.oopswap.domain.model.product.detail.select.ProductSelect
import com.swapit.oopswap.domain.repository.ProductRepository
import com.swapit.oopswap.ui.base.BaseViewModel
import com.swapit.oopswap.ui.base.BaseViewModelFactory

class MyProductSelectViewModel(repository: ProductRepository) : BaseViewModel() {
    private val _onSaleProducts = mutableStateOf<List<ProductSelect>>(emptyList())
    val onSaleProducts: List<ProductSelect> get() = _onSaleProducts.value
    private val _soldOutProducts = mutableStateOf<List<ProductSelect>>(emptyList())
    val soldOutProducts: List<ProductSelect> get() = _soldOutProducts.value

    init {
        safeLaunch {
            val onSaleResponse = repository.myOnSaleProductSelectResponse()
            val soldOutResponse = repository.mySoldOutProductSelectResponse()
            if (onSaleResponse.success && soldOutResponse.success) {
                Log.d(TAG, "상품 조회 성공")
                _onSaleProducts.value = repository.myOnSaleProductSelectResults()
                _soldOutProducts.value = repository.mySoldOutProductSelectResults()
            } else {
                Log.e(TAG, "상품 조회 실패")
            }
        }
    }

    companion object {
        private const val TAG = "MyProductSelectionViewModel"

        fun factory(repository: ProductRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                MyProductSelectViewModel(
                    repository = repository,
                )
            }
    }
}
