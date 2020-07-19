package com.aljon.ipeople.features.person.list

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aljon.ipeople.base.BaseViewModel
import com.aljon.ipeople.features.person.DisplayablePerson
import com.aljon.module.data.features.person.PersonRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PersonListViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : BaseViewModel() {

    private val _state by lazy {
        PublishSubject.create<PersonListState>()
    }
    val state: Observable<PersonListState> get() = _state

    private val _persons = MutableLiveData<List<DisplayablePerson>>()
    val persons: LiveData<List<DisplayablePerson>> get() = _persons

    override fun isFirstTimeUiCreate(bundle: Bundle?) {
        getPersons()
    }

    private fun getPersons() {
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
}
