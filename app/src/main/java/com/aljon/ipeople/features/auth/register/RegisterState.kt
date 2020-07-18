package com.aljon.ipeople.features.auth.register

sealed class RegisterState {

    object RegisterSuccessful : RegisterState()

    object FieldsAreEmpty : RegisterState()

    object NameIsEmpty : RegisterState()

    object EmailIsEmpty : RegisterState()

    object PasswordIsEmpty : RegisterState()

    object EmailIsInvalid : RegisterState()

    object PasswordIsInvalid : RegisterState()

    object ShowProgressLoading : RegisterState()

    object HideProgressLoading : RegisterState()

    data class Error(val throwable: Throwable) : RegisterState()
}
