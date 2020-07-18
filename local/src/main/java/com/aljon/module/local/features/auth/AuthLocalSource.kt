package com.aljon.module.local.features.auth

import com.aljon.module.domain.features.auth.models.Session
import com.aljon.module.domain.features.auth.models.User
import io.reactivex.Completable
import io.reactivex.Single

interface AuthLocalSource {
    fun login(
        username: String,
        password: String
    ): Single<User>

    fun register(
        user: User
    ): Single<Long>

    fun saveCredentials(user: Session): Single<Session>

    fun getUserSession(): Single<Session>

    fun logout(): Completable
}
