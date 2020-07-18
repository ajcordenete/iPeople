package com.aljon.module.local.features.auth

import androidx.room.EmptyResultSetException
import androidx.room.Transaction
import com.aljon.module.domain.features.auth.models.Session
import com.aljon.module.domain.features.auth.models.User
import com.aljon.module.domain.utils.OnErrorResumeNext
import com.aljon.module.local.AppDatabase
import com.aljon.module.local.features.auth.models.DBSession
import com.aljon.module.local.features.auth.models.DBUser
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthLocalSourceImpl @Inject constructor(
    private val database: AppDatabase
) : AuthLocalSource {

    override fun login(username: String, password: String): Single<User> {
        return database
            .userDao()
            .getUser(username, password)
            .map { DBUser.toDomain(it) }
    }

    override fun register(user: User): Single<Long> {
        return Single.create { emitter ->
            val dbUser = DBUser.fromDomain(user)
            val id = database
                .userDao()
                .insert(dbUser)

            emitter.onSuccess(id)
        }
    }

    @Transaction
    override fun saveCredentials(session: Session): Single<Session> {
        return Single.create { emitter ->
            val dbSession = DBSession.fromDomain(session)
            database
                .sessionDao()
                .logoutUser()
            database
                .sessionDao()
                .insertOrUpdate(dbSession)

            emitter.onSuccess(session)
        }
    }

    override fun getUserSession(): Single<Session> {
        return database
            .sessionDao()
            .getSession()
            .compose(
                OnErrorResumeNext<DBSession, EmptyResultSetException>(
                    DBSession.empty(),
                    EmptyResultSetException::class.java
                )
            )
            .map {
                DBSession.toDomain(it)
            }
    }

    @Transaction
    override fun logout(): Completable {
        return Completable.create {
            database.sessionDao().logoutUser()
            it.onComplete()
        }
    }
}
