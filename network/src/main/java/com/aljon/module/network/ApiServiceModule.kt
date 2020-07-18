package com.aljon.module.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiServiceModule {

    @Provides
    @Singleton
    fun providesBaseplateApiServices(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)
}
