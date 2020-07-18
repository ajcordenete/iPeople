package com.aljon.ipeople.features.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aljon.ipeople.base.BaseViewModel
import com.aljon.module.data.features.auth.AuthRepository
import com.aljon.module.domain.features.auth.models.Session
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _state by lazy {
        PublishSubject.create<MainState>()
    }

    val state: Observable<MainState> = _state

    private val _userSession by lazy {
        MutableLiveData<Session>()
    }

    val session: LiveData<Session> = _userSession

    override fun isFirstTimeUiCreate(bundle: Bundle?) {
        fetchUserSession()
    }

    private fun fetchUserSession() {
        authRepository
            .getUserSession()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _state.onNext(MainState.ShowProgressLoading)
            }
            .doFinally {
                _state.onNext(MainState.HideProgressLoading)
            }
            .subscribeBy(
                onSuccess = {
                    _userSession.value = it
                },
                onError = {
                    Timber.e(it)
                }
            )
            .apply { disposables.add(this) }
    }

    fun logoutUserSession() {
        authRepository
            .logout()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _state.onNext(MainState.ShowProgressLoading)
            }
            .doFinally {
                _state.onNext(MainState.HideProgressLoading)
            }
            .subscribeBy(
                onComplete = {
                    _state.onNext(MainState.LogoutSuccess)
                },
                onError = {
                    Timber.e(it)
                }
            )
            .apply { disposables.add(this) }
    }
}
