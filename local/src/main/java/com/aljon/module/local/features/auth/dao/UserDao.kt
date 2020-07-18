package com.aljon.module.local.features.auth.dao

import androidx.room.Dao
import androidx.room.Query
import com.aljon.module.local.base.BaseDao
import com.aljon.module.local.features.auth.models.DBUser
import io.reactivex.Single

@Dao
abstract class UserDao : BaseDao<DBUser> {

    @Query("SELECT * FROM ${DBUser.USER_TABLE_NAME} WHERE email= :email AND password= :password LIMIT 1")
    abstract fun getUser(email: String, password: String): Single<DBUser>

    @Query("SELECT * FROM ${DBUser.USER_TABLE_NAME} WHERE pk_id = :id LIMIT 1")
    abstract fun getUser(id: Long): Single<DBUser>
}
