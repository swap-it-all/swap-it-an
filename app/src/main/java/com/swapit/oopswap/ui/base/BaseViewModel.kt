package com.swapit.oopswap.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swapit.oopswap.data.datasource.remote.dto.response.ErrorResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import retrofit2.HttpException

open class BaseViewModel : ViewModel() {
    // UI 에러/알림용 이벤트 플로우
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    companion object {
        // 파싱 설정이 적용된 Json 인스턴스 (재사용)
        private val jsonParser =
            Json {
                ignoreUnknownKeys = true
            }
    }

    /**
     * suspend 블록 내부의 HttpException 을 잡아
     * ErrorResponse 로 파싱한 뒤 UiEvent.ShowError 로 emit
     */
    protected fun safeLaunch(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: HttpException) {
                val rawBody = e.response()?.errorBody()?.string().orEmpty()
                val error = try {
                    if (rawBody.isNotBlank()) {
                        // 정상적인 ErrorResponse JSON
                        jsonParser.decodeFromString<ErrorResponse>(rawBody)
                    } else {
                        // 빈 바디일 때는 임의로 메시지 구성
                        ErrorResponse(
                            status = e.code(),
                            errorCode = "UNKNOWN_ERROR",
                            message = e.message()
                        )
                    }
                } catch (parseEx: Exception) {
                    // JSON 파싱 실패까지 안전하게 처리
                    ErrorResponse(
                        status = e.code(),
                        errorCode = "PARSING_ERROR",
                        message = "서버 응답 해석 실패"
                    )
                }
                GlobalEventBus.post(UiEvent.ShowError(error))
            } catch (_: Exception) {
                // 기타 예외 필요 시 처리
            }
        }
    }
}
