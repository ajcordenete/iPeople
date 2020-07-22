package com.aljon.ipeople.features.person.list

sealed class PersonListState {

    data class GetName(val name: String) : PersonListState()

    object ShowLoading : PersonListState()

    object HideLoading : PersonListState()

    object ShowFetchError : PersonListState()

    object LogoutSuccessful : PersonListState()
}
