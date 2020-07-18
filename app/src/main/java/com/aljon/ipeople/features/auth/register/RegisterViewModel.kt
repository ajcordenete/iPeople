package com.aljon.ipeople.features.auth.register

import android.app.Application
import android.os.Bundle
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

    private lateinit var email: String

    private val _state by lazy {
        PublishSubject.create<RegisterState>()
    }

    val state: Observable<RegisterState> = _state

    override fun isFirstTimeUiCreate(bundle: Bundle?) {}

    fun register(password: String, mobileNumber: String) {
        disposables.add(repository.register(
            email = email,
            password = password,
            name = "",
            country = "")
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
            .subscribeBy(onSuccess = { user ->

                if (user.id.orEmpty().isNotEmpty()) {
                    _state.onNext(RegisterState.SaveLoginCredentials(user))
                }
            }, onError = {
                _state.onNext(RegisterState.Error(it))
            })
        )
    }
}
