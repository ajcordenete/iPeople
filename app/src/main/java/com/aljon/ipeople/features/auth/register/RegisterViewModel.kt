package com.aljon.ipeople.features.auth.register

import android.app.Application
import android.os.Bundle
import android.util.Patterns
import com.aljon.ipeople.base.BaseViewModel
import com.aljon.module.data.features.auth.AuthRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val app: Application
) : BaseViewModel() {
    private val _state by lazy {
        PublishSubject.create<RegisterState>()
    }

    val state: Observable<RegisterState> = _state

    override fun isFirstTimeUiCreate(bundle: Bundle?) {}

    fun register(email: String, password: String, name: String, country: String) {
        if (!validFields(email, password, name))
            return

        disposables.add(repository.register(
            email = email,
            password = password,
            name = name,
            country = country)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _state.onNext(RegisterState.ShowProgressLoading)
            }
            .doOnSuccess {
                _state.onNext(RegisterState.HideProgressLoading)
            }
            .doOnError {
                _state.onNext(RegisterState.HideProgressLoading)
            }
            .subscribeBy(
                onSuccess = { session ->
                    if (session.id.orEmpty().isNotEmpty()) {
                        _state.onNext(RegisterState.RegisterSuccessful)
                    }
            }, onError = {
                _state.onNext(RegisterState.Error(it))
            })
        )
    }

    private fun validFields(email: String, password: String, name: String): Boolean {
        if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {
            _state.onNext(RegisterState.FieldsAreEmpty)
            return false
        }

        if (name.isEmpty()) {
            _state.onNext(RegisterState.NameIsEmpty)
            return false
        }

        if (email.isEmpty()) {
            _state.onNext(RegisterState.EmailIsEmpty)
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.onNext(RegisterState.EmailIsInvalid)
            return false
        }

        if (password.isEmpty()) {
            _state.onNext(RegisterState.PasswordIsEmpty)
            return false
        }

        if (password.length < 6) {
            _state.onNext(RegisterState.PasswordIsInvalid)
            return false
        }

        return true
    }
}
