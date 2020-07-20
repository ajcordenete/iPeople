package com.aljon.ipeople.features.auth.login

import android.os.Bundle
import androidx.core.util.PatternsCompat
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
        if (!validFields(email, password))
            return

        repository
            .login(email, password)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribeBy(
                onSuccess = { session ->
                    if (session.id.orEmpty().isNotEmpty()) {
                        _state.onNext(LoginState.LoginSuccess)
                    }
                }, onError = {
                _state.onNext(LoginState.Error(it))
            })

            .apply { disposables.add(this) }
    }

    private fun validFields(email: String, password: String): Boolean {
        if (email.isEmpty() && password.isEmpty()) {
            _state.onNext(LoginState.FieldsAreEmpty)
            return false
        }

        if (email.isEmpty()) {
            _state.onNext(LoginState.EmailIsEmpty)
            return false
        }

        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.onNext(LoginState.EmailIsInvalid)
            return false
        }

        if (password.isEmpty()) {
            _state.onNext(LoginState.PasswordIsEmpty)
            return false
        }

        if (password.length < 6) {
            _state.onNext(LoginState.PasswordIsInvalid)
            return false
        }

        return true
    }
}
