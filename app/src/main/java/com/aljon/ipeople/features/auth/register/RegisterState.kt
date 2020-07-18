package com.aljon.ipeople.features.auth.register

sealed class RegisterState {

    object RegisterSuccessful : RegisterState()

    object ShowProgressLoading : RegisterState()

    object HideProgressLoading : RegisterState()

    data class Error(val throwable: Throwable) : RegisterState()
}
