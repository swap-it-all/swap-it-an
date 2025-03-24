package com.swapit.company.ui.shopping.detail.select

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swapit.company.domain.model.product.detail.select.ProductSelect
import com.swapit.company.domain.repository.ProductRepository
import com.swapit.company.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class MyProductSelectViewModel(repository: ProductRepository) : ViewModel() {
    private val _onSaleProducts = mutableStateOf<List<ProductSelect>>(emptyList())
    val onSaleProducts: List<ProductSelect> get() = _onSaleProducts.value
    private val _soldOutProducts = mutableStateOf<List<ProductSelect>>(emptyList())
    val soldOutProducts: List<ProductSelect> get() = _soldOutProducts.value

    init {
        viewModelScope.launch {
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
