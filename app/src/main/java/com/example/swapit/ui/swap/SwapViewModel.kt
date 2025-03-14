package com.example.swapit.ui.swap

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.swapit.data.datasource.remote.dto.request.swap.SwapRequest
import com.example.swapit.data.mapper.toDomain
import com.example.swapit.domain.model.swap.ReceivedSwap
import com.example.swapit.domain.model.swap.ReceivedSwapProduct
import com.example.swapit.domain.model.swap.SentSwap
import com.example.swapit.domain.repository.SwapRepository
import com.example.swapit.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class SwapViewModel(private val repository: SwapRepository) : ViewModel() {
    val requestedProductId = mutableLongStateOf(0)
    val targetProductId = mutableLongStateOf(0)
    private val _dialogStates = mutableStateOf(mutableMapOf<Long, Boolean>())
    val dialogStates: MutableState<MutableMap<Long, Boolean>> = _dialogStates
    var receivedSwap = mutableStateOf(emptyList<ReceivedSwap>())
    var sentSwap = mutableStateOf(emptyList<SentSwap>())
    var receivedSwapProductsResult = mutableStateOf(emptyList<ReceivedSwapProduct>())
    var myGoodsTitle = mutableStateOf("")

    fun openDialog(goodsId: Long) {
        _dialogStates.value =
            _dialogStates.value.toMutableMap().apply {
                this[goodsId] = true
            }
    }

    fun closeDialog(goodsId: Long) {
        _dialogStates.value =
            _dialogStates.value.toMutableMap().apply {
                this[goodsId] = false
            }
    }

    fun swapCancel(tradesId: Long) {
        viewModelScope.launch {
            repository.swapCancel(tradesId)
        }
    }

    fun swapAccept(tradesId: Long) {
        viewModelScope.launch {
            repository.swapAccept(tradesId)
        }
    }

    fun swapReject(tradesId: Long) {
        viewModelScope.launch {
            repository.swapReject(tradesId)
        }
    }

    fun swapComplete(tradesId: Long) {
        viewModelScope.launch {
            repository.swapComplete(tradesId)
        }
    }

    fun fetchReceivedSwap() {
        viewModelScope.launch {
            receivedSwap.value = repository.receivedSwap()
        }
    }

    fun fetchReceivedSwapProductsResult(goodsId: Long) {
        viewModelScope.launch {
            myGoodsTitle.value = repository.receivedSwapProductsResult(goodsId).myGoodsTitle
            receivedSwapProductsResult.value = repository.receivedSwapProductsResult(goodsId).goodsList.map { it.toDomain() }
        }
    }

    fun fetchSentSwap() {
        viewModelScope.launch {
            sentSwap.value = repository.sentSwap()
        }
    }

    fun swapRequest() {
        Log.d(TAG, "거래 시작")
        viewModelScope.launch {
            if (requestedProductId.longValue == 0L || targetProductId.longValue == 0L) {
                Log.e(TAG, "ID값 누락")
                return@launch
            }
            val request =
                repository.swapRequest(
                    swapRequest =
                        SwapRequest(
                            requestedGoodsId = requestedProductId.longValue,
                            targetGoodsId = targetProductId.longValue,
                        ),
                )
            if (request.success) {
                Log.d(TAG, "거래 성공")
            } else {
                Log.e(TAG, "거래 실패")
            }
        }
    }

    companion object {
        private const val TAG = "SwapProductViewModel"

        fun factory(repository: SwapRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                SwapViewModel(repository = repository)
            }
    }
}
