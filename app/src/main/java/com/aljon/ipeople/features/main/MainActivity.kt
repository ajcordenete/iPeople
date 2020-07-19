package com.aljon.ipeople.features.main

import android.content.Context
import android.content.Intent
import androidx.navigation.findNavController
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseViewModelActivity
import com.aljon.ipeople.databinding.ActivityMainBinding

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

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }
}
