package com.swapit.oopswap.data.datasource

import com.swapit.oopswap.data.datasource.remote.dto.request.user.ReportRequest
import com.swapit.oopswap.data.datasource.remote.dto.response.BaseResponse
import com.swapit.oopswap.data.datasource.remote.service.ReportService

class RemoteReportDataSource(private val reportService: ReportService) {
    suspend fun report(report: ReportRequest): BaseResponse<Unit> {
        val response = reportService.report(report)
        return if (response.success) {
            response
        } else {
            throw Exception(response.message)
        }
    }
}
