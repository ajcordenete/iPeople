package com.aljon.ipeople.di

import com.aljon.module.data.features.auth.AuthRepository
import com.aljon.module.data.features.auth.AuthRepositoryImpl
import com.aljon.module.data.features.person.PersonRepository
import com.aljon.module.data.features.person.PersonRepositoryImpl
import com.aljon.module.local.features.auth.AuthLocalSource
import com.aljon.module.network.features.person.PersonRemoteSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesAuthRepository(
        local: AuthLocalSource
    ): AuthRepository = AuthRepositoryImpl(local)

    @Provides
    fun providesPersonRepository(
        remote: PersonRemoteSource
    ): PersonRepository = PersonRepositoryImpl(remote)
}
