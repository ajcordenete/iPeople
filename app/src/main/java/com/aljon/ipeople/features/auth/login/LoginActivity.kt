package com.aljon.ipeople.features.auth.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelActivity
import com.aljon.ipeople.databinding.ActivityLoginBinding
import com.aljon.ipeople.features.auth.register.RegisterActivity
import com.aljon.ipeople.features.main.MainActivity
import com.aljon.module.common.ninjaTap
import com.aljon.module.common.toast
import com.aljon.module.common.widget.CustomPasswordTransformation
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class LoginActivity : BaseViewModelActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViews(savedInstanceState)
        setUpViewModels()
        setupToolbar()
    }

    private fun setUpViews(savedInstanceState: Bundle?) {
        binding.etPassword.apply {
            transformationMethod = CustomPasswordTransformation()
        }

        disposables.add(binding.btnContinue.ninjaTap {
            login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        })

        disposables.add(binding.signUp.ninjaTap {
            RegisterActivity.openActivity(this)
        })
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
                    handleState(state)
                },
                onError = {
                    Timber.e(it)
                }
            )
            .apply { disposables.add(this) }
    }

    private fun handleState(state: LoginState) {
        when (state) {
            is LoginState.LoginSuccess -> {
                MainActivity.openActivity(this)
                finishAffinity()
            }

            is LoginState.FieldsAreEmpty -> {
                binding.etEmailLayout.error = getString(R.string.email_must_not_be_empty)
                binding.etPasswordLayout.error = getString(R.string.password_must_not_be_empty)
            }

            is LoginState.EmailIsEmpty -> {
                binding.etEmailLayout.error = getString(R.string.email_must_not_be_empty)
            }

            is LoginState.EmailIsInvalid -> {
                binding.etEmailLayout.error = getString(R.string.invalid_email)
            }

            is LoginState.PasswordIsEmpty -> {
                binding.etPasswordLayout.error = getString(R.string.password_must_not_be_empty)
            }

            is LoginState.PasswordIsInvalid -> {
                binding.etPasswordLayout.error = getString(R.string.password_must_contain_minimum_characters)
            }

            is LoginState.Error -> {
                toast(getString(R.string.invalid_credentials))
            }
        }
    }

    private fun login(email: String, password: String) {
        clearErrors()
        viewModel.login(email, password)
    }

    private fun clearErrors() {
        binding.etEmailLayout.error = null
        binding.etPasswordLayout.error = null
    }
}
