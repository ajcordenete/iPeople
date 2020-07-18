package com.aljon.ipeople.features.auth.landing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelActivity
import com.aljon.ipeople.databinding.ActivityLandingBinding
import com.aljon.ipeople.features.auth.login.LoginActivity
import com.aljon.ipeople.features.main.MainActivity
import com.aljon.module.common.toast
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class LandingActivity : BaseViewModelActivity<ActivityLandingBinding, LandingViewModel>() {

    companion object {
        fun openActivity(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    LandingActivity::class.java
                )
            )
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_landing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel
            .state
            .observeOn(scheduler.ui())
            .subscribeBy(
                onNext = { state ->
                    handleState(state)
                },
                onError = {
                    Timber.e(it)
                }
            )
            .apply { disposables.add(this) }
    }

    private fun handleState(state: LandingState) {
        when (state) {
            is LandingState.UserIsLoggedIn -> {
                openMainActivity()
            }

            is LandingState.UserIsNotLoggedIn -> {
                openLoginActivity()
            }

            is LandingState.Error -> {
                toast(getString(R.string.generic_error))
            }
        }
    }

    private fun openMainActivity() {
        MainActivity.openActivity(this)
        finishAffinity()
    }

    private fun openLoginActivity() {
        LoginActivity.openActivity(this)
        finishAffinity()
    }
}
