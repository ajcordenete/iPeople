package com.aljon.ipeople.features.auth.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelActivity
import com.aljon.ipeople.databinding.ActivityRegisterBinding
import com.aljon.module.common.ninjaTap
import com.aljon.module.common.toast
import com.aljon.module.common.widget.CustomPasswordTransformation
import com.aljon.module.network.base.response.error.ResponseError
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class RegisterActivity : BaseViewModelActivity<ActivityRegisterBinding, RegisterViewModel>() {

    companion object {
        fun openActivity(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_register

    override fun canBack(): Boolean {
        return true
    }

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
    }
}
