package com.aljon.module.data.features.auth

import com.aljon.module.domain.features.auth.models.Session
import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {
    fun login(username: String, password: String): Single<Session>

    fun getUserSession(): Single<Session>

    fun register(
        email: String,
        password: String,
        name: String,
        country: String
    ): Single<Session>

    fun logout(): Completable
}
