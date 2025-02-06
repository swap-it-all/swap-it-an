package com.example.swap_it.ui.auth

sealed interface LoginState {
    data object None : LoginState

    data class Success(val token: String) : LoginState

    data class Failure(val message: String) : LoginState
}
