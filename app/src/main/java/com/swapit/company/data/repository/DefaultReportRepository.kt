package com.swapit.company.data.repository

import com.swapit.company.data.datasource.RemoteReportDataSource
import com.swapit.company.data.datasource.remote.dto.request.user.ReportRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.domain.repository.ReportRepository

class DefaultReportRepository(
    private val remoteSource: RemoteReportDataSource,
) : ReportRepository {
    override suspend fun report(report: ReportRequest): BaseResponse<Unit> {
        return remoteSource.report(report)
    }
}
