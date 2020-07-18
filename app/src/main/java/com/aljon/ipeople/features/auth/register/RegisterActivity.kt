package com.aljon.ipeople.features.auth.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelActivity
import com.aljon.ipeople.databinding.ActivityRegisterBinding
import com.aljon.ipeople.features.main.MainActivity
import com.aljon.module.common.ninjaTap
import com.aljon.module.common.toast
import com.aljon.module.common.widget.CustomPasswordTransformation
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

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
        setupViews()
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
                    handleState(it)
                },
                onError = {
                    Timber.e("Error $it")
                }
            ).apply { disposables.add(this) }
    }

    private fun setupViews() {
        binding.etPassword.apply {
            transformationMethod = CustomPasswordTransformation()
        }

        disposables.add(binding.btnContinue.ninjaTap {
            register()
        })

        val countryAdapter = ArrayAdapter(this,
            R.layout.item_country, getCountries())

        this.binding.etCountry.adapter = countryAdapter
    }

    private fun handleState(state: RegisterState) {
        when (state) {
            is RegisterState.RegisterSuccessful -> {
                openMainActivity()
            }

            is RegisterState.Error -> {
                toast(getString(R.string.generic_error))
            }
        }
    }

    private fun getCountries(): List<String> {
        val locales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country: String = locale.getDisplayCountry()
            if (country.trim().isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()
        return countries
    }

    private fun register() {
        viewModel.register(
            email = binding.etEmail.text.toString(),
            password = binding.etPassword.text.toString(),
            name = binding.etName.text.toString(),
            country = binding.etCountry.selectedItem.toString()
        )
    }

    private fun openMainActivity() {
        MainActivity.openActivity(this)
        finishAffinity()
    }
}
