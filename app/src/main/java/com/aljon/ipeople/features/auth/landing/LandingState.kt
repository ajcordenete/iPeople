package com.aljon.ipeople.features.auth.landing

sealed class LandingState {
    object UserIsLoggedIn : LandingState()

    object UserIsNotLoggedIn : LandingState()

    class Error(val throwable: Throwable) : LandingState()
}
