package com.aljon.ipeople.di

import com.aljon.module.data.features.auth.AuthRepository
import com.aljon.module.data.features.auth.AuthRepositoryImpl
import com.aljon.module.local.features.auth.AuthLocalSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesAuthRepository(
        local: AuthLocalSource
    ): AuthRepository = AuthRepositoryImpl(local)
}
