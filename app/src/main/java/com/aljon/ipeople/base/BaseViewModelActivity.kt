package com.aljon.ipeople.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.aljon.ipeople.ViewModelFactory
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * Automatically initializes ViewDataBinding class and ViewModel class for your activity.
 */
abstract class BaseViewModelActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseActivity<B>() {

    @Inject
    lateinit var factory: ViewModelFactory<VM>

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Gets the class type passed in VM parameter.
        // https://stackoverflow.com/a/52073780/5285687
        val viewModelClass = (javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[1] as Class<VM>

        viewModel = ViewModelProviders
                .of(this, factory)
                .get(viewModelClass)
        viewModel.onCreate(intent.extras)
    }
}
