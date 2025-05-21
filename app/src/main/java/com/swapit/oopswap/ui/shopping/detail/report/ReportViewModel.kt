package com.swapit.oopswap.ui.shopping.detail.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModelProvider
import com.swapit.oopswap.data.datasource.local.model.report.ReportOption
import com.swapit.oopswap.data.datasource.remote.dto.request.user.ReportRequest
import com.swapit.oopswap.domain.repository.ReportRepository
import com.swapit.oopswap.ui.base.BaseViewModel
import com.swapit.oopswap.ui.base.BaseViewModelFactory

class ReportViewModel(private val repository: ReportRepository) : BaseViewModel() {
    private val _reportMessage = mutableStateOf("")
    val reportMessage = _reportMessage

    fun reportProduct(
        goodsId: String,
        onResult: (Boolean) -> Unit,
    ) {
        safeLaunch {
            val response =
                repository.report(
                    ReportRequest(
                        goodsId.toLong(),
                        ReportOption.GOODS.name,
                        reportMessage.value,
                    ),
                )
            onResult(response.success)
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
