package com.aljon.ipeople.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.aljon.ipeople.R
import com.aljon.ipeople.utils.schedulers.BaseSchedulerProvider
import com.aljon.module.common.setVisible
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Automatically initializes ViewDataBinding class for your activity.
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    lateinit var binding: B

    protected val disposables: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var scheduler: BaseSchedulerProvider

    protected var toolbar: Toolbar? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            getLayoutId()
        )
        binding.lifecycleOwner = this
        setupToolbarAndStatusBar()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (canBack()) {
            if (item.itemId == android.R.id.home) {
                onBackPressed()
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

    fun hideToolbar() {
        if (toolbar != null) {
            toolbar?.setVisible(false)
        }
    }

    fun showToolbar() {
        if (toolbar != null) {
            toolbar?.setVisible(true)
        }
    }

    fun setToolbarBackgroundColor(@ColorRes color: Int) {
        if (toolbar != null) {
            toolbar?.setBackgroundColor(ContextCompat.getColor(this, color))
        }
    }

    private fun setupToolbarAndStatusBar() {
        toolbar = findViewById(R.id.toolbarView)
        if (toolbar != null) {
            toolbar = findViewById(R.id.toolbarView)
            setSupportActionBar(toolbar)
            if (canBack()) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                // TODO add navigation click rxbinding
            }
        }
    }

    fun setToolbarTitle(@StringRes res: Int, @ColorRes textColor: Int = R.color.colorPrimary) {
        if (supportActionBar != null) {
            val toolbarTitle = toolbar!!.findViewById<TextView>(R.id.toolbarTitle)

            if (toolbarTitle != null) {
                toolbarTitle.setText(res)
                toolbarTitle.setTextColor(
                    ResourcesCompat.getColor(this.resources, textColor, null)
                )
                supportActionBar?.title = null
            } else {
                supportActionBar?.setTitle(res)
            }
        }
    }

    fun setToolbarTitle(res: String) {
        if (supportActionBar != null) {
            val toolbarTitle = toolbar?.findViewById<TextView>(R.id.toolbarTitle)

            if (toolbarTitle != null) {
                toolbarTitle.text = res
                supportActionBar?.title = null
            } else {
                supportActionBar?.title = res
            }
        }
    }

    fun setToolbarNoTitle() {
        setToolbarTitle("")
    }

    fun setToolbarHomeIndicatorIcon(@DrawableRes iconRes: Int) {
        supportActionBar?.setHomeAsUpIndicator(iconRes)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    fun enableToolbarHomeIndicator() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    fun disableToolbarBackButton() {
        toolbar?.navigationIcon = null
        supportActionBar?.setHomeButtonEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
