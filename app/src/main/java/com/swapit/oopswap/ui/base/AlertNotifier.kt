package com.swapit.oopswap.ui.base

import com.swapit.oopswap.data.datasource.local.model.alert.AlertType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AlertNotifier {
    private val _alertState = MutableStateFlow<Pair<String, AlertType>?>(null)
    val alertState = _alertState.asStateFlow()

    fun notify(
        message: String,
        type: AlertType,
    ) {
        _alertState.value = message to type
    }

    fun clear() {
        _alertState.value = null
    }
}
