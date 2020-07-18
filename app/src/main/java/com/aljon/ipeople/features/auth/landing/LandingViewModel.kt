package com.aljon.ipeople.features.auth.landing

import android.os.Bundle
import com.aljon.ipeople.base.BaseViewModel
import com.aljon.module.data.features.auth.AuthRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class LandingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _state by lazy {
        PublishSubject.create<LandingState>()
    }

    val state: Observable<LandingState> = _state

    override fun isFirstTimeUiCreate(bundle: Bundle?) {
        authRepository
            .getUserSession()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribeBy(
                onSuccess = { session ->
                    if (session.id.isNullOrEmpty()) {
                        _state.onNext(LandingState.UserIsNotLoggedIn)
                    } else {
                        _state.onNext(LandingState.UserIsLoggedIn)
                    }
                },
                onError = {
                    Timber.e(it)
                }
            )
            .apply { disposables.add(this) }
    }
}
