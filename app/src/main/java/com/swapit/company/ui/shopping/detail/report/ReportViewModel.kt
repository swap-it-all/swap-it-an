package com.swapit.company.ui.shopping.detail.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swapit.company.data.datasource.local.model.report.ReportOption
import com.swapit.company.data.datasource.remote.dto.request.user.ReportRequest
import com.swapit.company.domain.repository.ReportRepository
import com.swapit.company.ui.base.BaseViewModelFactory
import kotlinx.coroutines.launch

class ReportViewModel(private val repository: ReportRepository) : ViewModel() {
    private val _reportMessage = mutableStateOf("")
    val reportMessage = _reportMessage

    fun reportProduct(
        goodsId: String,
        onResult: (Boolean) -> Unit,
    ) {
        viewModelScope.launch {
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
