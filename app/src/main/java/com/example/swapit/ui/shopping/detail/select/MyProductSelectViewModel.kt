package com.example.swapit.ui.shopping.detail.select

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.domain.model.shopping.ShoppingProduct
import com.example.swapit.domain.repository.MyProductSelectRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class MyProductSelectViewModel(repository: MyProductSelectRepository) : ViewModel() {
    private val _products = mutableStateOf<List<ShoppingProduct>>(emptyList())
    val products: List<ShoppingProduct> get() = _products.value

    init {
        viewModelScope.launch {
            val response = repository.myProductSelectResponse()
            if (response.success) {
                Log.d(TAG, "상품 조회 성공")
                _products.value = repository.myProductSelectResults()
            }
            else {
                Log.e(TAG, "상품 조회 실패")
            }
        }
    }

    companion object {
        private const val TAG = "MyProductSelectionViewModel"

        fun factory(repository: MyProductSelectRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                MyProductSelectViewModel(
                    repository = repository,
                )
            }
    }
}