package com.swapit.oopswap.ui.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AlertNotifier {
    private val _messageFlow = MutableStateFlow<String?>(null)
    val messageFlow = _messageFlow.asStateFlow()

    fun notify(message: String) {
        _messageFlow.value = message
    }

    fun clear() {
        _messageFlow.value = null
    }
}
