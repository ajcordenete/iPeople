package com.aljon.ipeople.features.person.list

sealed class PersonListState {

    object ShowLoading : PersonListState()

    object HideLoading : PersonListState()

    object ShowFetchError : PersonListState()
}
