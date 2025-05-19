package com.swapit.oopswap.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

/**
 * 네트워크 호출을 감싸서
 *  • 401 + REFRESH_TOKEN_EXPIRED → onLogout() 콜백
 *  • 나머지 에러는 그대로 Result.failure로 반환
 */
suspend fun <T> safeApiCall(
    onLogout: () -> Unit,
    apiCall: suspend () -> T
): Result<T> = withContext(Dispatchers.IO) {
    try {
        Result.success(apiCall())
    } catch (e: HttpException) {
        if (e.code() == 401) {
            // 서버 에러 바디에서 errorCode 추출
            val body = e.response()?.errorBody()?.string().orEmpty()
            val errCode = runCatching { JSONObject(body).getString("errorCode") }.getOrNull()
            if (errCode == "REFRESH_TOKEN_EXPIRED") {
                onLogout()  // 진짜 만료 시 로그아웃
            }
        }
        Result.failure(e)
    } catch (t: Throwable) {
        Result.failure(t)
    }
}
