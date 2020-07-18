package com.aljon.module.local.features.auth.dao

import androidx.room.Dao
import androidx.room.Query
import com.aljon.module.local.base.BaseDao
import com.aljon.module.local.features.auth.models.DBSession
import io.reactivex.Single

@Dao
abstract class SessionDao : BaseDao<DBSession> {

    @Query("SELECT * FROM ${DBSession.SESSION_TABLE_NAME} LIMIT 1")
    abstract fun getSession(): Single<DBSession>

    @Query("DELETE FROM ${DBSession.SESSION_TABLE_NAME}")
    abstract fun logoutUser()
}
