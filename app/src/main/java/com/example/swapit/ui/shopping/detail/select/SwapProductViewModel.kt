package com.example.swapit.ui.shopping.detail.select

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.domain.repository.SwapRequestRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class SwapProductViewModel(private val repository: SwapRequestRepository) : ViewModel() {
    val requestedProductId = mutableLongStateOf(0)
    val targetProductId = mutableLongStateOf(0)
    private val _dialogStates = mutableStateOf(mutableMapOf<Long, Boolean>())
    val dialogStates: MutableState<MutableMap<Long, Boolean>> = _dialogStates

    fun openDialog(goodsId: Long) {
        _dialogStates.value = _dialogStates.value.toMutableMap().apply {
            this[goodsId] = true
        }
    }

    fun closeDialog(goodsId: Long) {
        _dialogStates.value = _dialogStates.value.toMutableMap().apply {
            this[goodsId] = false
        }
    }


    fun swapRequest() {
        Log.d(TAG, "거래 시작")
        viewModelScope.launch {

            if (requestedProductId.longValue == 0L || targetProductId.longValue == 0L) {
                Log.e(TAG, "ID값 누락")
                return@launch
            }
            val response =
                repository.swapRequest(
                    swapRequest = SwapRequest(
                        requestedGoodsId = requestedProductId.longValue,
                        targetGoodsId = targetProductId.longValue
                    )
                )
            if (response.success) {
                Log.d(TAG, "거래 성공")
            } else {
                Log.e(TAG, "거래 실패")
            }
        }
    }

    companion object {
        private const val TAG = "SwapProductViewModel"

        fun factory(repository: SwapRequestRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                SwapProductViewModel(repository = repository)
            }
    }
}