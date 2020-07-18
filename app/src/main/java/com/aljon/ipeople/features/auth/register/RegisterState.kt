package com.aljon.ipeople.features.auth.register

import com.aljon.module.domain.features.auth.models.Session

sealed class RegisterState {

    data class GetEmail(val email: String) : RegisterState()

    data class SaveLoginCredentials(val user: Session) : RegisterState()

    data class Error(val throwable: Throwable) : RegisterState()

    object ShowProgressLoading : RegisterState()

    object HideProgressLoading : RegisterState()
}
