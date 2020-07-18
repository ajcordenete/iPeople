package com.aljon.module.local

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application.applicationContext)
    }
}
