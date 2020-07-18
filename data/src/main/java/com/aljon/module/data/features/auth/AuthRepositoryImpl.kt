package com.aljon.module.data.features.auth

import com.aljon.module.domain.features.auth.models.Session
import com.aljon.module.domain.features.auth.models.User
import com.aljon.module.local.features.auth.AuthLocalSource
import com.aljon.module.local.features.auth.mapper.toSession
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val local: AuthLocalSource
) : AuthRepository {

    override fun login(username: String, password: String): Single<Session> {
        return local
            .login(username, password)
            .flatMap {
                local.saveCredentials(it.toSession())
            }
    }

    override fun register(email: String, password: String, name: String, country: String): Single<Session> {
        val user = User(
            email = email,
            password = password,
            name = name,
            country = country
        )

        return local
            .register(user)
            .flatMap {
                login(email, password)
            }
    }

    override fun getUserSession(): Single<Session> {
        return local.getUserSession()
            .onErrorResumeNext(local.getUserSession())
    }

    override fun logout(): Completable {
        return local.logout()
    }
}
