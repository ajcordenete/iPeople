package com.aljon.ipeople.features.main

sealed class MainState {

    object LogoutSuccess : MainState()

    object ShowProgressLoading : MainState()

    object HideProgressLoading : MainState()
}
