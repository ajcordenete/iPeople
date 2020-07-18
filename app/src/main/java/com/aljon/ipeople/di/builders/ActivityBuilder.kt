package com.aljon.ipeople.di.builders

import com.aljon.ipeople.features.auth.landing.LandingActivity
import com.aljon.ipeople.features.auth.login.LoginActivity
import com.aljon.ipeople.features.auth.register.RegisterActivity
import com.aljon.ipeople.features.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector()
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector()
    abstract fun contributeRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeLandingActivity(): LandingActivity
}
