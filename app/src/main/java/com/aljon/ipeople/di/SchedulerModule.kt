package com.aljon.ipeople.di

import com.aljon.ipeople.utils.schedulers.BaseSchedulerProvider
import com.aljon.ipeople.utils.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule {

    @Provides
    @Singleton
    fun providesSchedulerSource(): BaseSchedulerProvider =
            SchedulerProvider.getInstance()
}
