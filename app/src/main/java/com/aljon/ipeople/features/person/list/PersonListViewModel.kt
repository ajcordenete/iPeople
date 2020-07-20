package com.aljon.ipeople.features.person.list

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aljon.ipeople.base.BaseViewModel
import com.aljon.ipeople.features.person.DisplayablePerson
import com.aljon.module.data.features.auth.AuthRepository
import com.aljon.module.data.features.person.PersonRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class PersonListViewModel @Inject constructor(
    private val personRepository: PersonRepository,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _state by lazy {
        PublishSubject.create<PersonListState>()
    }
    val state: Observable<PersonListState> get() = _state

    private val _persons = MutableLiveData<List<DisplayablePerson>>()
    val persons: LiveData<List<DisplayablePerson>> get() = _persons

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> get() = _name

    override fun isFirstTimeUiCreate(bundle: Bundle?) {
        getSession()
    }

    fun getPersons() {
        personRepository
            .getPersons()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _state.onNext(PersonListState.ShowLoading)
            }
            .doOnError {
                _state.onNext(PersonListState.HideLoading)
            }
            .doOnSuccess {
                _state.onNext(PersonListState.HideLoading)
            }
            .subscribeBy(
                onSuccess = { persons ->
                    _persons.value = persons.map { DisplayablePerson.fromDomain(it) }
                },
                onError = {
                    _state.onNext(PersonListState.ShowFetchError)
                }
            )
            .apply { disposables.add(this) }
    }

    fun getSession() {
        authRepository
            .getUserSession()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribeBy(
                onSuccess = { session ->
                    if (session.name.orEmpty().isNotEmpty()) {
                        _name.value = session.name
                    }
                },
                onError = {
                    Timber.e(it)
                }
            )
            .apply { disposables.add(this) }
    }

    fun logout() {
        authRepository
            .logout()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribeBy(
                onComplete = {
                    _state.onNext(PersonListState.LogoutSuccessful)
                },
                onError = {
                    Timber.e(it)
                }
            )
            .apply { disposables.add(this) }
    }
}
