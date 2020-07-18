package com.aljon.ipeople.features.auth.login

import android.os.Bundle
import com.aljon.ipeople.base.BaseViewModel
import com.aljon.module.data.features.auth.AuthRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel() {

    private lateinit var email: String

    override fun isFirstTimeUiCreate(bundle: Bundle?) {}

    private val _state by lazy {
        PublishSubject.create<LoginState>()
    }

    val state: Observable<LoginState> = _state

    fun login(email: String, password: String) {
        repository
            .login(email, password)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _state.onNext(LoginState.ShowProgressLoading)
            }
            .subscribeBy(
                onSuccess = { user ->
                    _state.onNext(LoginState.HideProgressLoading)

                    _state.onNext(LoginState.LoginSuccess(user))
                }, onError = {
                _state.onNext(LoginState.Error(it))
            })

            .apply { disposables.add(this) }
    }
}
