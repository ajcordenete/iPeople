package com.aljon.ipeople.features.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelActivity
import com.aljon.ipeople.databinding.ActivityLoginBinding
import com.aljon.ipeople.ext.disabledWithAlpha
import com.aljon.ipeople.ext.enableWhen
import com.aljon.ipeople.ext.enabledWithAlpha
import com.aljon.ipeople.features.auth.register.RegisterActivity
import com.aljon.ipeople.features.main.MainActivity
import com.aljon.module.common.NINJA_TAP_THROTTLE_TIME
import com.aljon.module.common.ninjaTap
import com.aljon.module.common.toast
import com.aljon.module.common.widget.CustomPasswordTransformation
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LoginActivity : BaseViewModelActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }

        const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViews(savedInstanceState)
        observeInputViews()
        setUpViewModels()
        setupToolbar()
    }

    private fun setUpViews(savedInstanceState: Bundle?) {
        binding.etPassword.apply {
            transformationMethod = CustomPasswordTransformation()
        }

        binding.etPassword.apply {
            setText(savedInstanceState?.getString(KEY_PASSWORD))
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (binding.btnContinue.isEnabled) {
                        viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                    }
                    true
                } else {
                    false
                }
            }
        }

        binding.btnContinue.enableWhen(binding.etEmail) {
            Patterns.EMAIL_ADDRESS.matcher(it).matches() && it.isNotEmpty()
        }

        disposables.add(binding.btnContinue.ninjaTap {
            viewModel.login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        })

        disposables.add(binding.signUp.ninjaTap {
            RegisterActivity.openActivity(this)
        })
    }

    private fun observeInputViews() {
        val passwordObservable = binding.etPassword.textChangeEvents()
            .skipInitialValue()
            .map { it.text }
            .map { it.isNotEmpty() && it.length >= 8 }

        disposables.add(
            passwordObservable
                .debounce(NINJA_TAP_THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .observeOn(scheduler.ui())
                .subscribeBy(onNext = {
                    if (it) {
                        binding.btnContinue.enabledWithAlpha()
                    } else {
                        binding.btnContinue.disabledWithAlpha()
                    }
                }, onError = {
                    Timber.e(it)
                })
        )
    }

    private fun setupToolbar() {
        setToolbarNoTitle()
    }

    private fun setUpViewModels() {
        viewModel
            .state
            .observeOn(scheduler.ui())
            .subscribeBy(
                onNext = { state ->
                    when (state) {
                        is LoginState.GetEmail -> {
                            binding.etEmail.apply {
                                setText(state.email)
                            }
                        }

                        is LoginState.LoginSuccess -> {
                            MainActivity.openActivity(this)
                            finishAffinity()
                        }

                        is LoginState.Error -> {
                            // show error message
                            toast(getString(R.string.invalid_credentials))
                        }

                        is LoginState.ShowProgressLoading -> {
                            toast("Sending request")
                        }
                    }
                },
                onError = {
                    Timber.e(it)
                }
            )
            .apply { disposables.add(this) }
    }
}
