package com.aljon.ipeople.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.aljon.ipeople.R
import com.aljon.ipeople.utils.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    lateinit var binding: B

    @Inject
    lateinit var scheduler: BaseSchedulerProvider

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun attachToParent(): Boolean = false

    val disposables: CompositeDisposable = CompositeDisposable()

    protected var toolbar: Toolbar? = null

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(),
            container,
            attachToParent()
        )
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbarAndStatusBar()
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (canBack()) {
            if (item.itemId == android.R.id.home) {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * @return true if should use back button on toolbar
     */
    protected open fun canBack(): Boolean {
        return false
    }

    private fun setupToolbarAndStatusBar() {
        toolbar = view?.findViewById(R.id.toolbarView)
        if (toolbar != null) {
            toolbar = view?.findViewById(R.id.toolbarView)
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            if (canBack()) {
                (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    fun setToolbarTitle(res: String) {
        val toolbarTitle = toolbar?.findViewById<TextView>(R.id.toolbarTitle)

        if (toolbarTitle != null) {
            toolbarTitle.text = res
            (activity as AppCompatActivity).supportActionBar?.title = null
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = null
        }
    }

    fun setToolbarNoTitle() {
        setToolbarTitle("")
    }
}
