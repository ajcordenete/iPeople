package com.aljon.ipeople.di

import android.content.Context
import com.aljon.ipeople.IpeopleApplication
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun providesApplicationContext(app: IpeopleApplication): Context
}
