package com.swapit.oopswap.data.repository

import com.swapit.oopswap.data.datasource.RemoteReportDataSource
import com.swapit.oopswap.data.datasource.remote.dto.request.user.ReportRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.domain.repository.ReportRepository

class DefaultReportRepository(
    private val remoteSource: RemoteReportDataSource,
    private val onLogout: () -> Unit
) : ReportRepository {
    override suspend fun report(report: ReportRequest): Result<BaseResponse<Unit>> =
        safeApiCall(onLogout) {
        remoteSource.report(report)
    }
}
