package com.aljon.ipeople.features.auth.login

import com.aljon.module.domain.features.auth.models.Session

sealed class LoginState {

    data class GetEmail(val email: String) : LoginState()

    data class LoginSuccess(val user: Session) : LoginState()

    data class UserNotVerified(val user: Session) : LoginState()

    data class Error(val throwable: Throwable) : LoginState()

    object NoUserFirstAndLastName : LoginState()

    object ShowProgressLoading : LoginState()

    object HideProgressLoading : LoginState()
}
