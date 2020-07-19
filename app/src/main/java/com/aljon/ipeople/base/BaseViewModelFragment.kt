package com.aljon.ipeople.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.aljon.ipeople.ViewModelFactory
import com.aljon.ipeople.di.Injectable
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * Base fragment class for fragments that has view models.
 *
 * Automatically initializes ViewDataBinding class and ViewModel class for your fragment.
 */
abstract class BaseViewModelFragment<B : ViewDataBinding, VM : BaseViewModel> :
    BaseFragment<B>(),
    Injectable {

    lateinit var viewModel: VM

    open fun useActivityViewModel(): Boolean = false

    @Inject
    lateinit var factory: ViewModelFactory<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Gets the class type passed in VM parameter.
        // https://stackoverflow.com/a/52073780/5285687
        val viewModelClass = (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[1] as Class<VM>
        viewModel = when {
            setActivityAsViewModelProvider() -> {
                ViewModelProviders
                    .of(requireActivity(), factory)
                    .get(viewModelClass)
            }
            setParentFragmentAsViewModelProvider() -> {
                ViewModelProviders
                    .of(requireParentFragment(), factory)
                    .get(viewModelClass)
            }
            else -> {
                ViewModelProviders
                    .of(this, factory)
                    .get(viewModelClass)
            }
        }.apply {
            onCreate(arguments)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    open fun setParentFragmentAsViewModelProvider(): Boolean {
        return false
    }

    open fun setActivityAsViewModelProvider(): Boolean {
        return false
    }
}
