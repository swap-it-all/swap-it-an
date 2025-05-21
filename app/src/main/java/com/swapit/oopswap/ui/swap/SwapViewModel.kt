package com.swapit.oopswap.ui.swap

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swapit.oopswap.data.datasource.remote.dto.request.swap.SwapRequest
import com.swapit.oopswap.data.mapper.toDomain
import com.swapit.oopswap.domain.model.swap.ReceivedSwap
import com.swapit.oopswap.domain.model.swap.ReceivedSwapProduct
import com.swapit.oopswap.domain.model.swap.SentSwap
import com.swapit.oopswap.domain.repository.SwapRepository
import com.swapit.oopswap.ui.base.BaseViewModel
import com.swapit.oopswap.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class SwapViewModel(private val repository: SwapRepository) : BaseViewModel() {
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
        safeLaunch {
            repository.swapCancel(tradesId)
        }
    }

    fun swapAccept(tradesId: Long) {
        safeLaunch {
            repository.swapAccept(tradesId)
        }
    }

    fun swapReject(tradesId: Long) {
        safeLaunch {
            repository.swapReject(tradesId)
        }
    }

    fun swapComplete(tradesId: Long) {
        safeLaunch {
            repository.swapComplete(tradesId)
        }
    }

    fun fetchReceivedSwap() {
        safeLaunch {
            receivedSwap.value = repository.receivedSwap()
        }
    }

    fun fetchReceivedSwapProductsResult(goodsId: Long) {
        safeLaunch {
            myGoodsTitle.value = repository.receivedSwapProductsResult(goodsId).myGoodsTitle
            receivedSwapProductsResult.value = repository.receivedSwapProductsResult(goodsId).goodsList.map { it.toDomain() }
        }
    }

    fun fetchSentSwap() {
        safeLaunch {
            sentSwap.value = repository.sentSwap()
        }
    }

    fun swapRequest() {
        // 1) ID 유효성 검사
        if (requestedProductId.longValue == 0L || targetProductId.longValue == 0L) {
            Log.e(TAG, "ID값 누락")
            return
        }

        // 2) 유효할 때만 safeLaunch 호출
        safeLaunch {
            val request = repository.swapRequest(
                SwapRequest(
                    requestedGoodsId = requestedProductId.longValue,
                    targetGoodsId    = targetProductId.longValue,
                )
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
