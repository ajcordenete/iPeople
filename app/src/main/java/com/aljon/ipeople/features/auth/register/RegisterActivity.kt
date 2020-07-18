package com.aljon.ipeople.features.auth.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelActivity
import com.aljon.ipeople.databinding.ActivityOnboardRegisterStep1Binding
import com.aljon.ipeople.ext.disabledWithAlpha
import com.aljon.ipeople.ext.enableWhen
import com.aljon.ipeople.ext.enabledWithAlpha
import com.aljon.module.common.NINJA_TAP_THROTTLE_TIME
import com.aljon.module.common.getNetworkCountryIso
import com.aljon.module.common.ninjaTap
import com.aljon.module.common.toast
import com.aljon.module.common.widget.CustomPasswordTransformation
import com.aljon.module.network.base.response.error.ResponseError
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RegisterActivity : BaseViewModelActivity<ActivityOnboardRegisterStep1Binding, RegisterViewModel>() {

    companion object {
        fun openActivity(context: Context, email: String) {
            val intent = Intent(context, RegisterActivity::class.java)
            intent.putExtra(KEY_EMAIL, email)
            context.startActivity(intent)
        }

        const val KEY_EMAIL = "email"
    }

    override fun getLayoutId(): Int = R.layout.activity_onboard_register_step1

    override fun canBack(): Boolean {
        return true
    }

    private var networkCountryIso = "US"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()

        setupViewModels()
        observeInputViews()
        setupData()
    }

    private fun setupToolbar() {
        enableToolbarHomeIndicator()
        setToolbarNoTitle()
    }

    private fun setupViewModels() {
        viewModel.state
            .observeOn(scheduler.ui())
            .subscribeBy(
                onNext = {
                    when (it) {
                        is RegisterState.GetEmail -> {
                            binding.etEmail.apply {
                                isEnabled = false
                                setText(it.email)
                            }
                        }

                        is RegisterState.Error -> {
                            // show error message
                            ResponseError.getError(it.throwable,
                                ResponseError.ErrorCallback(httpExceptionCallback = {
                                    toast(it)
                                }))
                        }
                        is RegisterState.ShowProgressLoading -> {
                            toast("Sending request")
                        }
                    }
                },
                onError = {
                    Timber.e("Error $it")
                }
            ).apply { disposables.add(this) }
    }

    private fun setupData() {
        networkCountryIso = getNetworkCountryIso()

        binding.btnContinue.enableWhen(binding.etMobile) {
            it.isNotEmpty()
        }

        binding.etMobile.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (binding.btnContinue.isEnabled) {
                        register()
                    }
                    true
                } else {
                    false
                }
            }
        }

        disposables.add(binding.btnContinue.ninjaTap {
            register()
        })
    }

    private fun register() {
        viewModel.register(
            password = binding.etPassword.text.toString(),
            mobileNumber = ""
        )
    }

    private fun observeInputViews() {
        binding.etPassword.apply {
            transformationMethod = CustomPasswordTransformation()
        }

        val passwordObservable = binding.etPassword.textChangeEvents()
            .skipInitialValue()
            .map { it.text }
            .map { it.isNotEmpty() && it.length >= 8 }

        val phoneNumberObservable = binding.etMobile.textChangeEvents()
            .skipInitialValue()
            .map { it.text }
            .map { it.isNotEmpty() }

        disposables.add(
            Observable.combineLatest(
                passwordObservable,
                phoneNumberObservable,
                BiFunction<Boolean, Boolean, Boolean> { passwordCount, phoneNumberCount ->
                    passwordCount && phoneNumberCount
                })
                .debounce(NINJA_TAP_THROTTLE_TIME, TimeUnit.MILLISECONDS)
                .observeOn(scheduler.ui())
                .subscribe({
                    if (it) {
                        binding.btnContinue.enabledWithAlpha()
                    } else {
                        binding.btnContinue.disabledWithAlpha()
                    }
                }, {
                    Timber.e(it)
                })
        )
    }
}
