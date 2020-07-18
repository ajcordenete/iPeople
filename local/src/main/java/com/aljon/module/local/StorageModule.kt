package com.aljon.module.local

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.aljon.module.local.features.auth.AuthLocalSource
import com.aljon.module.local.features.auth.AuthLocalSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {

    @Provides
    @Singleton
    fun providesSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
    }

    @Provides
    fun providesAuthLocalSource(database: AppDatabase):
        AuthLocalSource = AuthLocalSourceImpl(database)
}
