package com.swapit.company.data.datasource.remote.service

import com.swapit.company.data.datasource.remote.dto.request.user.ReportRequest
import com.swapit.company.data.datasource.remote.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {
    @POST("api/user/report")
    suspend fun report(
        @Body report: ReportRequest,
    ): BaseResponse<Unit>
}
