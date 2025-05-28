package com.swapit.oopswap.ui.base

import com.swapit.oopswap.data.datasource.remote.dto.response.ErrorResponse

sealed class UiEvent {
    data class ShowError(val error: ErrorResponse) : UiEvent()

    object DismissError : UiEvent()
}
