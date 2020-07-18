package com.aljon.ipeople.features.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelActivity
import com.aljon.ipeople.databinding.ActivityMainBinding
import com.aljon.ipeople.features.auth.landing.LandingActivity
import com.aljon.module.common.ninjaTap
import com.aljon.module.common.toast
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        fun openActivity(context: Context) {
            context.startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                )
            )
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListeners()
        setUpViewModelsObserver()
    }

    private fun setListeners() {
        binding
            .logout
            .ninjaTap {
                viewModel.logoutUserSession()
            }
            .apply { disposables.add(this) }
    }

    private fun setUpViewModelsObserver() {
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

        viewModel
            .session
            .observe(this, Observer { user ->
                val text =
                    getString(
                        R.string.welcome_to_main_screen,
                        user.name
                    )

                binding
                    .userInfo
                    .text = text
            })
    }

    private fun handleState(state: MainState?) {
        when (state) {
            is MainState.LogoutSuccess -> {
                LandingActivity.openActivity(this)
                finishAffinity()
            }
            is MainState.ShowProgressLoading -> {
                toast(getString(R.string.sending_request))
            }
        }
    }
}
