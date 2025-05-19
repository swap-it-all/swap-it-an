package com.swapit.oopswap.ui.shopping.detail.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swapit.oopswap.data.datasource.local.model.report.ReportOption
import com.swapit.oopswap.data.datasource.remote.dto.request.user.ReportRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.domain.repository.ReportRepository
import com.swapit.oopswap.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class ReportViewModel(private val repository: ReportRepository) : ViewModel() {
    private val _reportMessage = mutableStateOf("")
    val reportMessage = _reportMessage

    fun reportProduct(
        goodsId: String,
        onResult: (Boolean) -> Unit,
    ) {
        viewModelScope.launch {
            // ① 레포지토리 호출
            val result: Result<BaseResponse<Unit>> = repository.report(
                ReportRequest(
                    goodsId.toLong(),
                    ReportOption.GOODS.name,
                    reportMessage.value,
                )
            )

            // ② 성공/실패에 따라 콜백 호출
            val isSuccess = result
                .getOrNull()         // Result가 성공이면 BaseResponse를, 실패면 null
                ?.success            // BaseResponse.success (Boolean)
                ?: false             // 실패거나 BaseResponse가 null이면 false

            onResult(isSuccess)

            /*val response =
                repository.report(
                    ReportRequest(
                        goodsId.toLong(),
                        ReportOption.GOODS.name,
                        reportMessage.value,
                    ),
                )
            onResult(response.success)*/
        }
    }

    companion object {
        private const val TAG = "ReportViewModel"

        fun factory(repository: ReportRepository): ViewModelProvider.Factory =
            BaseViewModelFactory {
                ReportViewModel(
                    repository = repository,
                )
            }
    }
}
