package com.swapit.oopswap.ui.shopping.detail.select

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swapit.oopswap.domain.model.product.detail.select.ProductSelect
import com.swapit.oopswap.domain.repository.ProductRepository
import com.swapit.oopswap.ui.base.BaseViewModelFactory
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
            // ② Result 언랩하고 BaseResponse.success 체크
            val onSaleSuccess = onSaleResponse
                .getOrNull()              // 성공이면 BaseResponse, 실패면 null
                ?.success ?: false        // BaseResponse.success 또는 false
            val soldOutSuccess = soldOutResponse
                .getOrNull()
                ?.success ?: false

            if (onSaleSuccess && soldOutSuccess) {
                Log.d(TAG, "상품 조회 성공")

                // ③ 두 번째 호출: Result<List<ProductSelect>>
                _onSaleProducts.value = repository
                    .myOnSaleProductSelectResults()
                    .getOrNull()           // 성공하면 List<ProductSelect>, 실패면 null
                    .orEmpty()             // null 이면 빈 리스트

                _soldOutProducts.value = repository
                    .mySoldOutProductSelectResults()
                    .getOrNull()
                    .orEmpty()
            } else {
                Log.e(TAG, "상품 조회 실패")
            }

/*            if (onSaleResponse.success && soldOutResponse.success) {
                Log.d(TAG, "상품 조회 성공")
                _onSaleProducts.value = repository.myOnSaleProductSelectResults()
                _soldOutProducts.value = repository.mySoldOutProductSelectResults()
            } else {
                Log.e(TAG, "상품 조회 실패")
            }*/
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
