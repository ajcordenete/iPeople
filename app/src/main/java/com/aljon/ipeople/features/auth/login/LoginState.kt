package com.aljon.ipeople.features.auth.login

sealed class LoginState {

    object LoginSuccess : LoginState()

    object FieldsAreEmpty : LoginState()

    object EmailIsEmpty : LoginState()

    object PasswordIsEmpty : LoginState()

    object EmailIsInvalid : LoginState()

    object PasswordIsInvalid : LoginState()

    data class Error(val throwable: Throwable) : LoginState()

    object ShowProgressLoading : LoginState()

    object HideProgressLoading : LoginState()
}
