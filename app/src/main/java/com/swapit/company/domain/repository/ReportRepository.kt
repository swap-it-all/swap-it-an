package com.swapit.company.domain.repository

import com.swapit.company.data.datasource.RemoteReportDataSource
import com.swapit.company.data.datasource.remote.ServiceModule
import com.swapit.company.data.datasource.remote.dto.request.user.ReportRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import com.swapit.company.data.repository.DefaultReportRepository

interface ReportRepository {
    suspend fun report(report: ReportRequest): BaseResponse<Unit>

    companion object {
        private var instance: ReportRepository? = null

        fun instance(): ReportRepository {
            if (instance == null) {
                instance =
                    DefaultReportRepository(
                        remoteSource = RemoteReportDataSource(ServiceModule.reportService),
                    )
            }
            return instance!!
        }
    }
}